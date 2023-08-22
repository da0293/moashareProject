package com.moashare.service;

import org.springframework.stereotype.Service;

import com.moashare.dao.MemberDAO;
import com.moashare.dto.MemberDTO;

@Service
public class MemberService {
	private final MemberDAO mdao;
	
	public MemberService(MemberDAO mdao) {
		this.mdao=mdao;
	}
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
}
