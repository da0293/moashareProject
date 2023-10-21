package com.moashare.dto;

import java.sql.Timestamp;

import com.moashare.model.Board;
import com.moashare.model.HotBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HotBoardDTO {
	private Board board;
	
	@Builder
	public HotBoardDTO(HotBoard hotboard) {
		this.board=hotboard.getBoard();
		
	}
}
