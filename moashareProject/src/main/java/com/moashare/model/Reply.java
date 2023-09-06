package com.moashare.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import groovy.transform.builder.Builder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message="댓글 내용값은 필수입니다.")
	@Size(min = 1, max = 200)
	private String rcontent;
	
	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@CreationTimestamp
	private Timestamp reg_dt;
	
	@Builder
	public Reply(Long id, String rcontent, Board board, Member member, Timestamp reg_dt) {
		this.id=id;
		this.rcontent=rcontent;
		this.board=board;
		this.member=member;
		this.reg_dt=reg_dt;
	}
}
