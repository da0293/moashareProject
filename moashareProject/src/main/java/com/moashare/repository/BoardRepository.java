package com.moashare.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moashare.dto.BoardDTO;
import com.moashare.model.Board;


public interface BoardRepository extends JpaRepository<Board, Long> {

	Page<Board> findByTitleContaining(String searchKeyWord, Pageable pageable);
	
}
