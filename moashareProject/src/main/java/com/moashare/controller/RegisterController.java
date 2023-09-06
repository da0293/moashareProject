package com.moashare.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moashare.enumpackage.AuthType;
import com.moashare.model.Member;
import com.moashare.service.EmailService;
import com.moashare.service.MemberService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RegisterController {

	private final MemberService ms;
	private final EmailService es;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
//	private final MemberRepository memberRepository;
	
	
	public RegisterController(MemberService ms, EmailService es, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.ms=ms;
		this.es=es;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
//		this.memberRepository=memberRepository;
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		return "home/register";
	}

	@PostMapping("/register")
	public String registerOk(@Valid @ModelAttribute("member") Member member,Errors errors , Model model ) {
		
		if(errors.hasErrors()) {
			model.addAttribute("member",member);
			
			Map<String, String> validatorResult=ms.validateHandling(errors);
			for(String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			return "home/register";
		}
		String rawPassword=member.getPassword();
//		log.info(member.getEmail());
//		log.info(member.getNickname());
		String encPassword=bCryptPasswordEncoder.encode(rawPassword);
//		System.out.println(member.getEmail());
//		log.info( "패스워드 <<<<<<<<"+ member.getPassword());
		member = Member.builder()
		  .email(member.getEmail())
		  .password(encPassword)
		  .nickname(member.getNickname())
		  .auth(AuthType.ROLE_USER)
		  .build();
		ms.save(member);
		return "redirect:/login";
	}
		
//	이메일 인증
	@GetMapping("/register/emailVerify") 
	@ResponseBody
	public String emailVerify(@RequestParam("email")String email) throws UnsupportedEncodingException, MessagingException {
		String authCode = es.sendEmail(email);
		return authCode;

	}
//	이메일 중복 검사
	@PostMapping("/register/emailCk")
	@ResponseBody
	public int emailCk(@RequestParam("email")String email) {
		System.out.println(email);
		int confirm = 0;
		if (ms.emailConfirm(email)==true){ // 이메일이 중복일 시 
			confirm=1; 
		} else { // 중복이 아닐 시 
			confirm=2; 
		}
		return confirm;
	}
	
//	닉네임 중복 검사
	@PostMapping("/register/nicknameCk") 
	@ResponseBody
	public int nicknameCk(@RequestParam("nickname")String nickname) {
		int confirm = 0;
		if(ms.nicknameConfirm(nickname)==true) {
			confirm=1;
		}else {
			confirm=2; 
		}
		return confirm; // 존지(중복)일 시 1로 리턴 
	}
}
