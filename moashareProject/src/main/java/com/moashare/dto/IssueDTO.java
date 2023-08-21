package com.moashare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueDTO {
	private Long ino;
	private String iname; 
	private String iwriter;
	private String icontent;
	private String istatus; 
	private String reg_dt;
	private String up_dt;
	private Long project_pno;
}
