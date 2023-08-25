package com.moashare.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moashare.config.auth.PrincipalDetails;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginForm() {
		return "home/login";
	}
	
	@GetMapping("/home")
	public String member(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principalDetils : " +principalDetails.getDto());
		return "home/homepage";
	}

	
	@GetMapping("/admin")
	public String admin() {
		return "home/adminpage";
	}

}
