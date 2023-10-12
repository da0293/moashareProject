package com.moashare.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="제목은 필수입력값입니다.")
	@Size(min = 1, max = 100)
	private String ntitle;
	
	@NotBlank(message="내용은 필수입력값입니다.")
	@Column(columnDefinition = "LONGTEXT")
	private String ncontent;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;
	
	@CreationTimestamp
	private Timestamp reg_dt;
	
	@Builder
	public Notice(Long id, String ntitle, String ncontent, Member member , Timestamp reg_dt ) {
		this.id=id;
		this.ntitle=ntitle;
		this.ncontent=ncontent;
		this.member=member;
		this.reg_dt=reg_dt;
		
	}
}
