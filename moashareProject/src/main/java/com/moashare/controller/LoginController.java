package com.moashare.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.dto.MemberDTO;
import com.moashare.service.EmailService;
import com.moashare.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	private final MemberService ms;
	private final EmailService es;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public LoginController(MemberService ms, EmailService es, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.ms=ms;
		this.es=es;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	
	@GetMapping("/login")
	public String loginForm() {
		return "home/login";
	}

   @PostMapping("/login/fail")
   public String loginFail(HttpServletRequest request, Model model) {
	   String errorMsg = (String) request.getAttribute("errorMsg");
	   String error="error";
	   model.addAttribute("errorMsg",errorMsg);
	   model.addAttribute("error",error);
       return "home/login";
   }
	
	@GetMapping("/admin")
	public String admin() {
		return "home/adminpage";
	}
	
	@GetMapping("/resetPw")
	public String resetPw() {
		return "home/resetPw";
	}
	
	@PostMapping("/resetPw")
	public String resetOk(@RequestParam("repw")String password,
			@RequestParam("email")String email) {
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 이메일 " + email );
		password=bCryptPasswordEncoder.encode(password);
		ms.resetPassword(email, password);
		return "redirect:/login";
	}
	
//	@PostMapping("/resetPw/emailCk")
//	@ResponseBody
//	public int emailCheck(@RequestParam("email")String emailId) {
//		int confirm = ms.emailCk(emailId);
//		return confirm; // 존재(중복)할 시 1로 리턴 
//	}
//	
	@GetMapping("/resetPw/emailVerify")
	@ResponseBody
	public String emailVerify(@RequestParam("email")String email) throws UnsupportedEncodingException, MessagingException {
		String authCode = es.sendEmail(email);
		return authCode;

	}
}
