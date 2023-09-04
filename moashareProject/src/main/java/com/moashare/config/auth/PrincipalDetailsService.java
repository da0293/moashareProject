package com.moashare.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moashare.model.Member;
import com.moashare.repository.MemberRepository;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	private MemberRepository memberRepository;
	
	public PrincipalDetailsService(MemberRepository memberRepository) {
		this.memberRepository=memberRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member memberEntity=memberRepository.findByEmail(username);
		if(memberEntity!=null) {
			return new PrincipalDetails(memberEntity);
		}
		return null;
	}

}
