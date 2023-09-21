package com.moashare.dto;


import java.sql.Timestamp;

import com.moashare.model.Board;
import com.moashare.model.Bookmark;
import com.moashare.model.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkDTO {
	private Long id;
	private Board board;
	private Member member;
	private boolean status;
	private Timestamp reg_dt;
	
	@Builder
	public BookmarkDTO(Long id, Board board, Boolean status , Member member,Timestamp reg_dt) {
		this.id=id;
		this.board=board;
		this.member=member;
		this.status=status;
		this.reg_dt=reg_dt;
	}
}
