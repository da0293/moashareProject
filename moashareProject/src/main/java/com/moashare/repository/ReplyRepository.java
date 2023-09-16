package com.moashare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.moashare.model.Reply;

import jakarta.transaction.Transactional;


public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	// 인테페이스에선  public 생략가능
	@Modifying
	@Transactional
	@Query(value="insert into Reply (member_id,board_id,rcontent,reg_dt) values (?1,?2,?3,now())", nativeQuery=true)
	int replySave(Long member_id, Long board_id, String rcontent); // insert, update, delete하면 업데이트한 행 개수 리턴해줌.)
}
