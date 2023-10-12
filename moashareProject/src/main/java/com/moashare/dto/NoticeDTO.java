package com.moashare.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeDTO {

	@NotBlank(message="제목은 필수입력값입니다.")
	@Size(min = 1, max = 100)
	private String ntitle;
	
	@NotBlank(message="내용은 필수입력값입니다.")
	private String ncontent;
	
}
