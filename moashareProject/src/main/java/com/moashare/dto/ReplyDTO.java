package com.moashare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
	private Long id; 
	private Long member_id;
	private Long board_id;
	private String rcontent;
}
