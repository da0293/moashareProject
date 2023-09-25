package com.moashare.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.service.BoardService;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReplyController {
	
	private final BoardService boardService; // RestController에선 private final 불가
	
	public ReplyController(BoardService boardService) {
		this.boardService=boardService;
	}
	
	@PostMapping("/reply/list/{boardId}")
	public String getBoard(@PathVariable Long boardId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info("board" +boardId);
		model.addAttribute("replyList", boardService.getReplyList(boardId));
		model.addAttribute("id",principalDetails.getMember().getId());
		log.info("마침");
		return "board/detail :: #replyTable"; 

	}
}
