package com.moashare.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.moashare.model.Board;
import com.moashare.model.Member;
import com.moashare.repository.BoardRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardService {

	 private final BoardRepository boardRepository;
	 
	 public BoardService(BoardRepository boardRepository) {
		 this.boardRepository=boardRepository;
	 }
	 
	 @Transactional // 함수 종료 시 자동 commit
	 public void saveBoard(Board board) {
		 boardRepository.save(board);
	 }

	public List<Board> boardList(Pageable pageable) {
		return boardRepository.findAll();
	}
	 
}
