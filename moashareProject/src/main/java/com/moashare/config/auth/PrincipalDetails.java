package com.moashare.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.moashare.dto.MemberDTO;

import lombok.Getter;
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

	private MemberDTO dto;
	private Map<String, Object> attributes;
	
	// 일반로그인
	public PrincipalDetails(MemberDTO dto) {
		this.dto=dto;
	}
	
	// OAuth로그인
	public PrincipalDetails(MemberDTO dto, Map<String, Object> attributes) {
		this.dto=dto;
		this.attributes=attributes;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect=new ArrayList<>();
		collect.add(new GrantedAuthority() {			
			@Override
			public String getAuthority() {
				return dto.getAuth();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return dto.getPw();
	}

	@Override
	public String getUsername() {
		return dto.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
	
}
