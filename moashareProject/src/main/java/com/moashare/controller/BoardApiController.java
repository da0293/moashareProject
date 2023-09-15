package com.moashare.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.controller.BoardApiController;
import com.moashare.dto.ResponseDTO;
import com.moashare.model.Board;
import com.moashare.model.Member;
import com.moashare.model.Reply;
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
		
		Board board = Board.builder()
				.title(title)
				.content(content)
				.member(principalDetails.getMember())
				.build();
		boardService.saveBoard(board);
		return new ResponseDTO<Integer>(HttpStatus.OK,1); // 정상성공시 1로 리턴 
		
	}
	
	@DeleteMapping("/boardApi/board/{id}")
	public ResponseDTO<Integer> deleteyById(@PathVariable Long id){
		boardService.deleteBoard(id);
		return new ResponseDTO<Integer>(HttpStatus.OK,1); 
	}
	
	@PutMapping("/boardApi/board/{id}") // delete와 같아도 메서드가 달라 괜찮다.
	public ResponseDTO<Integer> update(@PathVariable Long id, @RequestBody Board requestBoard){
		boardService.updateBoard(id,requestBoard);
		return new ResponseDTO<Integer>(HttpStatus.OK,1); 
		
	}
	
	// 댓글 작성
	@PostMapping("/boardApi/board/{boardId}/reply")
	public ResponseDTO<Integer> replySave(@RequestParam("rcontent") String rcontent,
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PathVariable Long boardId) {
		log.info("여기");
		boardService.saveReply(principalDetails.getMember(), boardId, rcontent);
		return new ResponseDTO<Integer>(HttpStatus.OK,1); // 정상성공시 1로 리턴 
		
	}
	
}
