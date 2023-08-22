package com.moashare.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moashare.service.EmailService;
import com.moashare.service.MemberService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
public class RegisterController {

	private final MemberService ms;
	private final EmailService emailService;

	
	
	@PostMapping("/register/emailCk")
	@ResponseBody
	public int emailCheck(@RequestParam("email")String emailId) {
		log.info("<<<<<<<<<<<< emailId  " + emailId);
		int confirm = ms.emailCk(emailId);
		log.info("<<<<<<<<< confirm : "+ confirm);
		return confirm; // 존지(중복)일 시 1로 리턴 
	}
	
	@GetMapping("/register/emailVerify")
	@ResponseBody
	public void emailVerify(@RequestParam("email")String email) throws UnsupportedEncodingException, MessagingException {
		log.info("<<<<<<<<<<<<<< emailVerify : " + email);
		String authCode = emailService.sendEmail(email);
		log.info("<<<<<<<<<<<<<<<<<<<< authcode : " + authCode);

	}
	
	@PostMapping("/register/nicknameCk")
	@ResponseBody
	public int nicknameCheck(@RequestParam("nickname")String nickname) {
		log.info("<<<<<<<<<<<< 닉네임 :   " + nickname);
		int confirm = ms.nicknameCk(nickname);
		log.info("<<<<<<<<< confirm : "+ confirm);
		return confirm; // 존지(중복)일 시 1로 리턴 
	}
}
