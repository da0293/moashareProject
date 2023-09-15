package com.moashare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moashare.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
}
