package com.moashare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.dto.BoardDTO;
import com.moashare.dto.BookmarkDTO;
import com.moashare.model.Board;
import com.moashare.model.Bookmark;
import com.moashare.model.Member;
import com.moashare.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookmarakController {
	
	private BoardService boardService;
	
	public BookmarakController(BoardService boardService) {
		this.boardService=boardService;
	}

	
	// 북마크 페이지
	@GetMapping("/bookmark")
	public String bookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model,
							@PageableDefault(page=0, size = 12, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
							String searchKeyWord) {
		Page<BookmarkDTO> bookmarkList=null;
		// 특정 아이디에 대한 북마크 리스트 가져오기
		log.info("여기옴");
		log.info("<<<<<<<<<<<<<<<<<<< 아이디 : " +principalDetails.getMember().getId() );
		bookmarkList=boardService.bookmarkCkList(principalDetails.getMember().getId(),pageable);
		
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("bookmarkList", bookmarkList);
		return "board/bookmark";
	}
	
	
	// 북마크 추가, 삭제
	@PostMapping("/board/{boardId}/bookmark")
	@ResponseBody
	public String updateBookmark(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		log.info("<<<<<<<<<<<<<<<<<<<<<<<< boardId : " +boardId);
		// 북마크가 된 상태면 추가, 이미있을 시 삭제 
		String bookmarkStatus= boardService.likeBoard(boardId, principalDetails.getMember().getId());
//		model.addAttribute("replyList", boardService.getReplyList(boardId));
//		model.addAttribute("id",principalDetails.getMember().getId());
		return bookmarkStatus; 
//		return "board :: #bookmarkTable"; 
	}

	@PostMapping("/board/list/{boardId}/bookmark")
	public String getBoard(@PathVariable Long boardId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info("board" +boardId);
		String bookmarkStatus=boardService.likeBoard(boardId, principalDetails.getMember().getId());
		if ( bookmarkStatus.equals("n")) {
			model.addAttribute("bookmarkList", boardService.likeBoard(boardId, principalDetails.getMember().getId()));
			
		}
		Board board=Board.builder()
				.title("test")
				.content("tcontent")
				.build();
	
				
		Bookmark bookmark=Bookmark.builder()
				.board(board)
				.build();
		
		bookmark.getBoard().getId();
		model.addAttribute("id",principalDetails.getMember().getId());
		log.info("마침");
		
		return "board/detail :: #bookmarkTable"; 

	}
}
