package com.moashare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
	private Long cmno;
	private String cmwriter;
	private String cmcontent;
	private String reg_dt;
	private String up_dt;
	private Long issue_ino;

	
	
}
