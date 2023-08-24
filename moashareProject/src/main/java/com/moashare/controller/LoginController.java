package com.moashare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	
	// 스프링시큐리티 해당주소 낚아챔 
	@GetMapping("/login")
	public String loginForm() {
		return "home/login";
	}
	
	@GetMapping("/home")
	public String member() {
		return "home/homepage";
	}
	@GetMapping("/admin")
	public String admin() {
		return "home/adminpage";
	}

}
