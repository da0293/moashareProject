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
}
