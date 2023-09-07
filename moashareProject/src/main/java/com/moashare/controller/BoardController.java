package com.moashare.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moashare.config.auth.PrincipalDetails;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class BoardController {
	@GetMapping("/board")
	public String board(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		return "board/board";
	}
	
	@GetMapping("/board/writeForm")
	public String boardWriteForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		return "board/boardWriteForm";
	}
	
//	@PostMapping("/board/writeOk")
//	@ResponseBody
//	public void writeOk(@RequestParam("title")String title, @RequestParam("content")String content) {
//		log.info("title <<<<<<<<<<<<<"+ title);
//		log.info("contennt " + content);
//	}
}
