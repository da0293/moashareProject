package com.moashare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDTO {

	private Long notino;
	private String ntitle;
	private String ncontent;
	private int nhits;
	private String reg_dt;
	private String up_dt;
	private String member_id;
}
