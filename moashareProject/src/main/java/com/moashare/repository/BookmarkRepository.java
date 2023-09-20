package com.moashare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moashare.model.Board;
import com.moashare.model.Bookmark;
import com.moashare.model.Member;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>{
	public Bookmark findByBoardAndMember(Board board, Member member);
}
