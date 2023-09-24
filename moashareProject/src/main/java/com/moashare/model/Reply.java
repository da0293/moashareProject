package com.moashare.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
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
	
	// 메서드명 update라고 해야지만 update반영 아닐 시 네이티브쿼리 이용
	public void update(Member member, Board board, String rcontent) {
		this.member=member;
		this.board=board;
		this.rcontent=rcontent;
	}
	
	public void update(String rcontent) {
		this.rcontent=rcontent;
	}

	@Override
	public String toString() {
		return "Reply [id=" + id + ", rcontent=" + rcontent + ", board=" + board + ", member=" + member + ", reg_dt="
				+ reg_dt + "]";
	}
}
