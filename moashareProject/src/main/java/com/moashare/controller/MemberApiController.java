package com.moashare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moashare.dto.ResponseDTO;
import com.moashare.model.Member;
import com.moashare.service.MemberService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class MemberApiController {
	private final MemberService memberService;
	
	public MemberApiController(MemberService memberService) {
		this.memberService=memberService;
	}
	
	@PutMapping("/memberProfile")
	public ResponseDTO<Integer> updateProfile(@RequestBody Member member){
		log.info("id : " + member.getId());
		log.info("pwd : " + member.getPassword());
		log.info("닉네임 : " + member.getNickname());
		memberService.updateMember(member);
		return new ResponseDTO<Integer>(HttpStatus.OK,1); 
	}
	
}
