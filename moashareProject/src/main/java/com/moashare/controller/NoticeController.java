package com.moashare.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.service.NoticeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class NoticeController {
	
	private final NoticeService noticeService;
	
	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@GetMapping("/notice")
	public String notice(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		model.addAttribute("noticeList", noticeService.getNoticeList());
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		return "notice/notice";
	}
	
	@GetMapping("/notice/{noticeId}")
	public String findById(@PathVariable Long noticeId, Model model,
			 HttpServletRequest req, HttpServletResponse res,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("notice", noticeService.detailView(noticeId));
		return "notice/detailNotice";
		
	}

}
