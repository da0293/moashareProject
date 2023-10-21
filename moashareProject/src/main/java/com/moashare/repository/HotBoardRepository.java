package com.moashare.repository;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.moashare.model.HotBoard;


// CRUD 함수를 JpaRepository가 들고 있음
// @Repository라는 어노테이션이 없어도 IOC된다. 

public interface HotBoardRepository extends JpaRepository<HotBoard, Long> {

	boolean existsByBoardId(Long id);

	@Transactional
	@Query(value = "select * from hot_board h USE INDEX (idx_reg_dt) where reg_dt > (CURRENT_DATE - 7) order by h.reg_dt desc", nativeQuery = true)
	List<HotBoard> findAllByRegDtAfter();

	
}
