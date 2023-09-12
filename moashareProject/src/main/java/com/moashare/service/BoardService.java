package com.moashare.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moashare.model.Board;
import com.moashare.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class BoardService {

	 private final BoardRepository boardRepository;
	 
	 public BoardService(BoardRepository boardRepository) {
		 this.boardRepository=boardRepository;
	 }
	 
	 // 게시판 글 저장
	 @Transactional // 함수 종료 시 자동 commit
	 public void saveBoard(Board board) {
		 boardRepository.save(board);
	 }
	 
	// 게시판 목록 가져오기
	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	// 게시판 목록(검색 시)가져오기
	@Transactional(readOnly = true)
	public Page<Board> boardSearchList(String searchKeyWord, Pageable pageable) {
		return boardRepository.findByTitleContaining(searchKeyWord,pageable);
	}

	// 게시판 상세페이지
	public Board detailView(Long id) {

		return boardRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException("아이디를 찾을 수 없어 글을 볼 수 없습니다.");
				});
	}
	
	// 게시판 삭제
	@Transactional
	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}

	// 게시판 수정
	@Transactional
	public void updateBoard(Long id, Board requestBoard) {
		Board board=boardRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException("아이디가 없어 글 찾기를 실패하였씁니다.");
				}); // 영속화 완료
		board.update(requestBoard.getTitle(),requestBoard.getContent());

		// 해당 함수 종료시(Service가 종료될 때) 트랜잭션 종료, 이 때 더티체킹함(자동 업데이트)
	}

}
