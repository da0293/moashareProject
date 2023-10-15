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

// CRUD 함수를 JpaRepository가 들고 있음
// @Repository라는 어노테이션이 없어도 IOC된다. 

public interface BoardRepository extends JpaRepository<Board, Long> {

	Page<Board> findByTitleContaining(String searchKeyWord, Pageable pageable);
	@Transactional
	@Modifying
	@Query(value="update board b set b.hits = b.hits+1 where b.id = :id", nativeQuery = true)
	int updateHits(@Param(value = "id") Long id);
	
	@Transactional
	@Query(value = "select * from board where hits > 0 and reg_dt > (NOW() - INTERVAL 7 DAY) order by id desc;", nativeQuery = true)
	List<Board> findAllByHotBoard();
}
