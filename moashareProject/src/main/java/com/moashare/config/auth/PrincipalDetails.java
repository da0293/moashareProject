package com.moashare.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.moashare.dto.MemberDTO;

public class PrincipalDetails implements UserDetails {

	private MemberDTO dto;
	
	public PrincipalDetails(MemberDTO dto) {
		this.dto=dto;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect=new ArrayList<>();
		collect.add(new GrantedAuthority() {			
			@Override
			public String getAuthority() {
				System.out.println("PrincipalDetails ok");
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
	
}
