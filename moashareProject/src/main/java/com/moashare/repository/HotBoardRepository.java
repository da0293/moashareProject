package com.moashare.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.moashare.model.HotBoard;


// CRUD 함수를 JpaRepository가 들고 있음
// @Repository라는 어노테이션이 없어도 IOC된다. 

public interface HotBoardRepository extends JpaRepository<HotBoard, Long> {

	boolean existsByBoardId(Long id);

	
}
