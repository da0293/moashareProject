package com.moashare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.controller.BoardApiController;
import com.moashare.dto.ResponseDTO;
import com.moashare.model.Board;
import com.moashare.model.Member;
import com.moashare.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BoardApiController {
	
	private BoardService boardService;
	
	public BoardApiController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@PostMapping("/boardApi/writeOk")
	public ResponseDTO<Integer> writeOk(@RequestParam("title")String title, @RequestParam("content")String content, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		log.info("title <<<<<<<<<<<<<"+ title);
		log.info("cont " + content);
		
		Board board = Board.builder()
				.title(title)
				.content(content)
				.member(principalDetails.getMember())
				.build();
		boardService.saveBoard(board);
		return new ResponseDTO<Integer>(HttpStatus.OK,1); // 성공시 1로 리턴 
		
	}
}