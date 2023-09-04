package com.moashare.model;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
	
	@Id //primarykey
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@NotNull(message="이메일은 필수 입력값입니다.")
	@Email(message="올바른 이메일 주소를 입력하세요.")
	private String email;
	
	@NotNull(message="비밀번호를 입력해주세요.")
 	private String password; 
	
	@NotNull(message="닉네임은 필수 입력값입니다.")
	@Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$" , message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
	private String nickname;
	
	private String auth;
	
	private String provider;
	
	@CreationTimestamp
	private String join_dt;
	
	@Builder
	public Member(Long id ,String email ,String password, String nickname, String auth, String provider, String join_dt) {
		this.id=id; 
		this.email=email;
		this.password=password;
		this.nickname=nickname;
		this.auth=auth;
		this.provider=provider;
		this.join_dt=join_dt;
	}
	
}