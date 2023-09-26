package com.moashare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyDTO {
	private Long id; 
	private Long member_id;
	private Long board_id;
	
	@NotBlank(message="내용은 필수입력값입니다.")
	@Size(min = 1, max = 200)
	private String rcontent;
}
