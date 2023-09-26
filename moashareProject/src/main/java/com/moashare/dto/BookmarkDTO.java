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
	
	@Builder
	public BookmarkDTO(Long id, Board board, Member member) {
		this.id=id;
		this.board=board;
		this.member=member;
	}
}
