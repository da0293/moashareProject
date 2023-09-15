package com.moashare.controller;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.dto.ResponseDTO;
import com.moashare.model.Member;
import com.moashare.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class MemberApiController {
	private final MemberService memberService;
	private final AuthenticationManager authenticationManager;
	
	public MemberApiController(MemberService memberService, AuthenticationManager authenticationManager) {
		this.memberService=memberService;
		this.authenticationManager=authenticationManager;
	}
	
	@PutMapping("/memberProfile")
	public ResponseDTO<Integer> updateProfile(@RequestBody Member member){
		memberService.updateMember(member);
		// 여기서는 트랜잭션이 종료되기때문에 DB값은 변경이 되었음
		// 하지만 세션값은 변경되지않은 상태이기 때무에 우리가 직접 세션값을 변경시켜야 한다.
		
		// 세션 강제 등록
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDTO<Integer>(HttpStatus.OK,1); 
	}
	
}
