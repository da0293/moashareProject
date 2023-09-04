package com.moashare.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
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
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	

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

	public void save(@Valid Member member) {
		memberRepository.save(member);
		
	}




}
