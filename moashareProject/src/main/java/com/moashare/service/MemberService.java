package com.moashare.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.moashare.dao.MemberDAO;
import com.moashare.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberDAO mdao;
	
	public int emailCk(String emailId) {
		MemberDTO dto = mdao.getMemberByEmail(emailId);
		return confirmCk(dto);
	}
	
	public int nicknameCk(String nickname) {
		MemberDTO dto = mdao.getMemberByNickname(nickname);		
		return confirmCk(dto);
	}

	public int confirmCk(MemberDTO dto) {
		int confirm=0;
		if(dto != null) confirm=1; // 이메일 아이디가 존재할 시(중복일 시) 1로 체크
		return confirm;
	}

	public void addOne(MemberDTO dto) {
		mdao.insertOne(dto);
	}
}
