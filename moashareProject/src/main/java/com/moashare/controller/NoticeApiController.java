package com.moashare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.controller.NoticeApiController;
import com.moashare.dto.NoticeDTO;
import com.moashare.dto.ReplyDTO;
import com.moashare.dto.ResponseDTO;
import com.moashare.model.Board;
import com.moashare.model.Notice;
import com.moashare.service.BoardService;
import com.moashare.service.NoticeService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NoticeApiController {
	
	private final NoticeService noticeService;
	
	public NoticeApiController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@PostMapping("/NoticeApi/writeOk")
	public ResponseDTO<Integer> writeOk(@Valid @RequestBody NoticeDTO noticeDTO,Errors errors, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		Notice notice = Notice.builder()
				.ntitle(noticeDTO.getNtitle())
				.ncontent(noticeDTO.getNcontent())
				.member(principalDetails.getMember())
				.build();
		noticeService.saveNotice(notice);
		return new ResponseDTO<Integer>(HttpStatus.OK,1); // 정상성공시 1로 리턴 
		
	}
	@DeleteMapping("/noticeApi/notice/{id}")
	public ResponseDTO<Integer> deleteyById(@PathVariable Long id){
		noticeService.deleteNotice(id);
		return new ResponseDTO<Integer>(HttpStatus.OK,1); 
	}
}
