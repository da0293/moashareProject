package com.moashare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	
	@GetMapping("/login")
	public String loginForm() {
		return "home/login";
	}
	
}
