package com.moashare.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.service.BoardService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class BoardController {
	
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService=boardService;
	}
	
	@GetMapping("/board")
	public String board(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model,
							@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("board", boardService.boardList(pageable));
		return "board/board";
	}
	
	@GetMapping("/board/writeForm")
	public String boardWriteForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		return "board/boardWriteForm";
	}
	

}
