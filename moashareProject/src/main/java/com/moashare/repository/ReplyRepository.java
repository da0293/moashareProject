package com.moashare.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.moashare.model.Board;
import com.moashare.model.Bookmark;
import com.moashare.model.Member;
import com.moashare.model.Reply;

import jakarta.transaction.Transactional;


public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	// 인테페이스에선  public 생략가능
	// Modfiying은 int만
	@Modifying
	@Transactional
	@Query(value = "insert into Reply (member_id,board_id,rcontent,reg_dt) values (?1,?2,?3,now())", nativeQuery=true)
	int replySave(Long member_id, Long board_id, String rcontent); // insert, update, delete하면 업데이트한 행 개수 리턴해줌.)

	@Query(value=" select * from reply r where board_id=?", nativeQuery = true)
	List<Reply> findAllByOrderByBoardDesc(Long board_id);


}
