package com.moashare.repository;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.moashare.model.Board;
import com.moashare.model.HotBoard;


// CRUD 함수를 JpaRepository가 들고 있음
// @Repository라는 어노테이션이 없어도 IOC된다. 

public interface HotBoardRepository extends JpaRepository<HotBoard, Long> {

	boolean existsByBoardId(Long id);

	@Transactional
	@Query(value = "select * from hot_board h where reg_dt > (CURRENT_DATE - 7) order by h.reg_dt desc", nativeQuery = true)
	Page<HotBoard> findAllByRegDtAfter(Pageable pageable);


	@Transactional
	@Modifying
    @Query(value = "INSERT INTO hot_board (board_id, title, hits, replycnt, reg_dt) " +
            "SELECT b.id, b.title, b.hits, b.replycnt, b.reg_dt " +
            "FROM board b " +
            "LEFT JOIN hot_board h ON b.id = h.board_id " +
            "WHERE b.hits > 150 " +
            "AND b.reg_dt > (NOW() - INTERVAL 1 DAY) " +
            "AND h.id IS NULL", nativeQuery = true)
	void findByBoardIdIsNullAndHitsGreaterThanAndRegDtAfter();
}
