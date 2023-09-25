package com.moashare.dto;

import java.sql.Timestamp;

import com.moashare.enumpackage.AuthType;
import com.moashare.model.Board;
import com.moashare.model.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDTO {
	private Long id;
	private String email;
	private String password;
	private String provider;
	
	@Builder
	public MemberDTO(Long id, String email, String password, String provider) {
		this.id=id;
		this.email=email;
		this.password=password;
		this.provider=provider;
	}

}
