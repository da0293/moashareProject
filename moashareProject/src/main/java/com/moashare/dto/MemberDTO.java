package com.moashare.dto;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
	private String id; 
	private String pw; 
	private String nickname;
	private String auth;
	private String provider;
	private String join_dt;
	
	@Builder
	public MemberDTO(String id, String pw, String nickname, String auth, String provider, String join_dt) {
		this.id=id; 
		this.pw=pw;
		this.nickname=nickname;
		this.auth=auth;
		this.provider=provider;
		this.join_dt=join_dt;
	}


}
