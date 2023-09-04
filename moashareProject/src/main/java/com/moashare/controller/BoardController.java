package com.moashare.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.moashare.config.auth.PrincipalDetails;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class BoardController {
	@GetMapping("/board")
	public String board(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
//		model.addAttribute("nickname", principalDetails.getDto().getNickname());
//		System.out.println("이름 : " +principalDetails.getDto());
		return "board/board";
	}
}
