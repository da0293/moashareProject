package com.moashare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String member() {
		return "home/homepage";
	}

	
	@GetMapping("/admin")
	public String admin() {
		return "home/adminpage";
	}

}
