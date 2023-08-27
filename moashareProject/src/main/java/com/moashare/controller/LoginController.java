package com.moashare.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moashare.config.auth.PrincipalDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginForm() {
		System.out.println("여기");
		return "home/login";
	}
	
	@GetMapping("/home")
	public String member(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		return "home/homepage";
	}

	
	@GetMapping("/admin")
	public String admin() {
		return "home/adminpage";
	}

}
