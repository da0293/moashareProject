package com.moashare.dto;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
	
	private String id; 
	private String pw; 
	private String name;
	private String nickname;
	private String tel;
	private String auth;
	private String join_dt;
	
	@Builder
	public MemberDTO(String id, String pw, String name, String nickname, String tel, String auth, String join_dt) {
		this.id=id; 
		this.pw=pw;
		this.name=name;
		this.nickname=nickname;
		this.tel=tel;
		this.auth=auth;
		this.join_dt=join_dt;
	}


}
