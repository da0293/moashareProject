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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Bookmark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Board board;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;
	
	@Column(nullable = false)
	private boolean status;
	
	@CreationTimestamp
	private Timestamp reg_dt;
	
	@Builder
	public Bookmark(Long id, Board board, Member member,Boolean status ,Timestamp reg_dt) {
		this.id=id;
		this.board=board;
		this.member=member;
		this.status=true;
		this.reg_dt=reg_dt;
	}
	
	public void unLikedBookmark(Board board) {
		this.status=false;
	}
}
