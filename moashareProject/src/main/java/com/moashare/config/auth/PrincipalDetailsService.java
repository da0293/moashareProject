package com.moashare.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moashare.dao.MemberDAO;
import com.moashare.dto.MemberDTO;
@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private MemberDAO dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String id=username;
		MemberDTO dto= dao.getMemberByEmail(id);
		if(dto!=null) {
			return new PrincipalDetails(dto);
		}
		return null;
	}

}
