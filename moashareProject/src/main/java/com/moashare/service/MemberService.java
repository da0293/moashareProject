package com.moashare.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.moashare.model.Member;
import com.moashare.repository.MemberRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.memberRepository=memberRepository;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}

	public boolean emailConfirm(String email) {
		System.out.println("Service : " + email);
		return memberRepository.existsByEmail(email);
	}
	
	public boolean nicknameConfirm(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}
	
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();
		for(FieldError error : errors.getFieldErrors()) {
			String validKeyName=String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		return validatorResult;
	}
	

	public void resetPassword() {
//		MemberDTO dto
//		mdao.modifyPassword(dto);
		
	}

	@Transactional
	public void save(@Valid Member member) {
		memberRepository.save(member);
		
	}
	@Transactional
	public void updateMember(Member member) {
		// 수정 시에는 영속성 컨테스트 Member 오브젝트를 영속화시키고 영속화된 Member 오브젝트 수정
		// Select해서 Member 오브젝트를 DB로부터 가져오는 이유는 영속화하기 위해서다.
		// 영속화를 하면 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
		Member persistance=memberRepository.findById(member.getId()).orElseThrow(()-> {
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		String rawPassword=member.getPassword();
		String encPassword=bCryptPasswordEncoder.encode(rawPassword);
		persistance.update(member.getNickname(),encPassword);
		// 회원 수정 함수 종료시 = 서비스 종류 = 트랜잭션 종료 = 커밋 자동
		// = 영속화된 persistance객체의 변화가 감지되면 더티체킹이 되서 update문을 자동으로 날려줌
		
	}




}
