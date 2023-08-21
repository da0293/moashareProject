package com.moashare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class HomeController {
	@GetMapping("/home")
	public String main(String[] args) {
		return "home/home";
	}

}
