package com.moashare.controller;

import java.io.UnsupportedEncodingException;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moashare.dto.MemberDTO;
import com.moashare.service.EmailService;
import com.moashare.service.MemberService;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RegisterController {

	private final MemberService ms;
	private final EmailService es;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public RegisterController(MemberService ms, EmailService es, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.ms=ms;
		this.es=es;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		return "home/register";
	}

	@PostMapping("/register")
	public String registerOk(@ModelAttribute("dto")MemberDTO dto) {
		String rawPassword=dto.getPw();
		String encPassword=bCryptPasswordEncoder.encode(rawPassword);
		dto.setPw(encPassword);
		ms.addOne(dto);
		return "redirect:/login";
	}
	
	@PostMapping("/register/emailCk")
	@ResponseBody
	public int emailCheck(@RequestParam("email")String emailId) {
		int confirm = ms.emailCk(emailId);
		return confirm; // 존지(중복)일 시 1로 리턴 
	}
	
	@GetMapping("/register/emailVerify")
	@ResponseBody
	public String emailVerify(@RequestParam("email")String email) throws UnsupportedEncodingException, MessagingException {
		String authCode = es.sendEmail(email);
		return authCode;

	}
	
	@PostMapping("/register/nicknameCk")
	@ResponseBody
	public int nicknameCheck(@RequestParam("nickname")String nickname) {
		int confirm = ms.nicknameCk(nickname);
		return confirm; // 존지(중복)일 시 1로 리턴 
	}
}