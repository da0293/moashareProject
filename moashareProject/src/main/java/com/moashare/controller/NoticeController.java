package com.moashare.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.moashare.config.auth.PrincipalDetails;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class NoticeController {
	
	@GetMapping("/notice")
	public String member(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		log.info("컨트롤러");
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		return "notice/notice";
	}
}
