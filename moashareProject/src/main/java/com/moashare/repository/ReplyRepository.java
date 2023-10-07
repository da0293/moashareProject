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
	
	List<Reply> findAllByBoardId(Long board_id);
}
