package com.moashare.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moashare.model.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

	// select * from member where email=?
	public Member findByEmail(String username); 

	boolean existsByNickname(String nickname);

	boolean existsByEmail(String email);

}
