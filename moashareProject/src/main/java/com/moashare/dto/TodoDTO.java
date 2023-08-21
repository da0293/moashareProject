package com.moashare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDTO {
	private Long tno;
	private String tname;
	private String tstatus;
	private String start_dt;
	private String end_dt;
	private String reg_dt;
	private String up_dt;
	private Long issue_ino;
}
