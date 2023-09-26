package com.moashare.dto;

import java.sql.Timestamp;

import com.moashare.model.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardDTO {
	private Long id;
	private String title;
	private String content;
	private int hits;
	private int replycnt;
	private Timestamp reg_dt;
	
	@Builder
	public BoardDTO(Board board) {
		this.id=board.getId();
		this.title=board.getTitle();
		this.content=board.getContent();
		this.hits=board.getHits();
		this.replycnt=board.getReplycnt();
		this.reg_dt=board.getReg_dt();
		
	}
}
