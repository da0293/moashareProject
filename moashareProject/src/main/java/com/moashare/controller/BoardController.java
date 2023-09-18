package com.moashare.controller;

import java.net.http.HttpRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.model.Board;
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
		Page<Board> board= null;
		if(searchKeyWord==null) {
			board=boardService.boardList(pageable);
		} else {
			board=boardService.boardSearchList(searchKeyWord,pageable);
		}
		int nowPage=board.getPageable().getPageNumber()+1;
		int startPage=Math.max(nowPage-4, 1);
		int endPage=Math.max(nowPage+5, board.getTotalPages());
		
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("board", board);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "board/board";
	}

	@GetMapping("/board/writeForm")
	public String boardWriteForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		return "board/boardWriteForm";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable Long id, Model model,
			 HttpServletRequest req, HttpServletResponse res,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info("<<<<<<<<<<<<<<<< 여기");
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("id",principalDetails.getMember().getId());
		model.addAttribute("board", boardService.detailView(id));
		boardService.updateView(id, req, res);
		return "board/detail";
		
	}
	@GetMapping("/board/{id}/update")
	public String updateForm(@PathVariable Long id, Model model) {
		model.addAttribute("board", boardService.detailView(id));
		return "board/updateForm";
	}

}
