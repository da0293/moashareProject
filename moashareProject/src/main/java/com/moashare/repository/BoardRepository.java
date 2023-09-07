package com.moashare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moashare.model.Board;
import com.moashare.model.Member;

// CRUD 함수를 JpaRepository가 들고 있음
// @Repository라는 어노테이션이 없어도 IOC된다. 
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

	
	
}
