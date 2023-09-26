package com.moashare.controller;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.dto.BoardDTO;
import com.moashare.dto.BookmarkDTO;
import com.moashare.dto.ReplyDTO;
import com.moashare.model.Board;
import com.moashare.model.Bookmark;
import com.moashare.model.Reply;
import com.moashare.service.BoardService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class BoardController {
	
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService=boardService;
	}
	
	// 페이징처리
	@GetMapping("/board")
	public String board(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model,
							@PageableDefault(page=0, size = 12, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
							String searchKeyWord) {
		Page<BoardDTO> boardList= null;
		if(searchKeyWord==null) {
			boardList=boardService.boardList(pageable);
		} else {
			boardList=boardService.boardSearchList(searchKeyWord,pageable);
		}
		// 해당 아이디가 가진 북마크리스트 가져옴
		List<Bookmark> bookmarkList=boardService.bookmarkList(principalDetails.getMember().getId());
		List<Long> bookmarks=new ArrayList<>();
		HashMap<Long, Boolean> map =new HashMap<>();
		for( Bookmark bookmark :  bookmarkList) {
			//bookmarks.add(bookmark.getBoard().getId());
			map.put(bookmark.getBoard().getId(),bookmark.isStatus());
		}
		for( Bookmark bookmark :  bookmarkList) {
			bookmarks.add(bookmark.getBoard().getId());
		}
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("boardList", boardList);
		return "board/board";
	}
	// 게시글 작성
	@GetMapping("/board/writeForm")
	public String boardWriteForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		return "board/boardWriteForm";
	}
	// 게시글 상세보기
	@GetMapping("/board/{id}")
	public String findById(@PathVariable Long id, Model model,
			 HttpServletRequest req, HttpServletResponse res,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info("<<<<<<<<<<<<<<<< 여기");
		model.addAttribute("replyList", boardService.getReplyList(id));
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("id",principalDetails.getMember().getId());
		model.addAttribute("board", boardService.detailView(id));
		boardService.updateView(id, req, res);
		return "board/detail";
		
	}
	// 게시글 수정
	@GetMapping("/board/{id}/update")
	public String updateForm(@PathVariable Long id, Model model) {
		model.addAttribute("board", boardService.detailView(id));
		return "board/updateForm";
	}

	// 북마크 페이지
	@GetMapping("/bookmark")
	public String bookmark(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model,
							@PageableDefault(page=0, size = 12, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
							String searchKeyWord) {
		Page<BoardDTO> boardList= null;
		// 특정 아이디에 대한 북마크 리스트 가져오기
		List<Bookmark> bookmarkList=boardService.bookmarkList(principalDetails.getMember().getId());
		if (bookmarkList!=null) {
			List<Long> bookmarks=new ArrayList<>();
			for( Bookmark bookmark :  bookmarkList) {
				bookmarks.add(bookmark.getBoard().getId());
			}
			// 해당 북마크체크된 보드 객체 가져오기 
			boardList = boardService.bookmarkList(bookmarks,pageable);
//			if(searchKeyWord==null) {
//				boardList=boardService.boardList(pageable);
//			} else {
//				boardList=boardService.bookmarkSearchList(searchKeyWord,pageable);
//			}
		}
		
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("boardList", boardList);
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
	
//	@PostMapping("/board/{boardId}/reply")
//	public String replySave(@RequestBody ReplyDTO replyDTO, @PathVariable Long boardId, Model model,  @AuthenticationPrincipal PrincipalDetails principalDetails) {
//		boardService.saveReply(replyDTO);
//		// 댓글리스트 추가 
//		model.addAttribute("replyList", boardService.getReplyList(boardId));
//		model.addAttribute("id",principalDetails.getMember().getId());
//		
//		log.info("<<<<<<<<<<<<< 달성 ");
//		return "board/detail :: #replyTable"; // 정상성공시 1로 리턴 
//		
//	}
}
