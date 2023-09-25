package com.moashare.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moashare.model.Member;

// CRUD 함수를 JpaRepository가 들고 있음
// @Repository라는 어노테이션이 없어도 IOC된다. 
public interface MemberRepository extends JpaRepository<Member, Long> {

	// select * from member where email=?
	public Member findByEmail(String username); 

	boolean existsByNickname(String nickname);

	boolean existsByEmail(String email);

	@Transactional
	@Modifying
	@Query(value="update member m set m.password = :password where m.email = :email", nativeQuery = true)
	public void resetPassword(@Param(value="email")String email,@Param(value="password") String password);
//	boolean exexistsById(String id);
	
	
}
