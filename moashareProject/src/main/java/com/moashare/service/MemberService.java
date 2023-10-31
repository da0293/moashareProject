package com.moashare.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.moashare.model.Member;
import com.moashare.repository.BoardRepository;
import com.moashare.repository.MemberRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final MessageSource messageSource;

	
	public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder, BoardRepository boardRepository, MessageSource messageSource) {
		this.memberRepository=memberRepository;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
		this.boardRepository=boardRepository;
		this.messageSource=messageSource;
	}

	@Transactional
	public boolean emailConfirm(String email) {
		System.out.println("Service : " + email);
		return memberRepository.existsByEmail(email);
	}
	
	@Transactional
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
	
	@Transactional
	public void resetPassword(String email, String newPassword) {
		Member member = memberRepository.findByEmail(email);
        if (member != null) {
            Member updatedMember = member.toBuilder()
                .password(newPassword)
                .build();
            memberRepository.save(updatedMember); 
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("memberNotFound", null, LocaleContextHolder.getLocale()));
        }
	}

	@Transactional
	public void save(@Valid Member member) {
		memberRepository.save(member);
		
	}
	@Transactional
	public void updateMember(Member member) {
		Member persistance=memberRepository.findById(member.getId()).orElseThrow(()-> {
			return new IllegalArgumentException(messageSource.getMessage("memberNotFound", null, LocaleContextHolder.getLocale()));
		});
		persistance.update(member.getNickname());
		
	}


}
