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
import com.moashare.controller.BoardApiController;
import com.moashare.dto.ReplyDTO;
import com.moashare.dto.ResponseDTO;
import com.moashare.model.Board;
import com.moashare.service.BoardService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BoardApiController {
	
	private BoardService boardService;
	
	public BoardApiController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@PostMapping("/boardApi/writeOk")
	public ResponseDTO<Integer> writeOk(@Valid @RequestBody Board board,Errors errors, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		Board firstBoard = Board.builder()
				.title(board.getTitle())
				.content(board.getContent())
				.member(principalDetails.getMember())
				.build();
		boardService.saveBoard(firstBoard);
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
	@PostMapping("/boardApi/{boardId}/reply")
	public ResponseDTO<Integer> replySave(@Valid @RequestBody ReplyDTO replyDTO, Model model, Errors errors,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		boardService.saveReply(replyDTO);
		return new ResponseDTO<Integer>(HttpStatus.OK,1);
		
	}
	
	
	@DeleteMapping("/boardApi/board/{boardId}/reply/{replyId}")
	public ResponseDTO<Integer> replyDelte(@PathVariable Long replyId){
		boardService.deleteReply(replyId);
		return new ResponseDTO<Integer>(HttpStatus.OK,1);
	}
	
	@PutMapping("/boardApi/board/{boardId}/reply/{replyId}")
	public ResponseDTO<Integer> replyUpdate(@PathVariable Long replyId, @RequestBody ReplyDTO replyDTO){
		boardService.updateReply(replyId,replyDTO);
		return new ResponseDTO<Integer>(HttpStatus.OK,1);
	}
}
