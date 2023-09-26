package com.moashare.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moashare.dto.BookmarkDTO;
import com.moashare.model.Board;
import com.moashare.model.Bookmark;
import com.moashare.model.Member;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>{
	public Bookmark findByBoardAndMember(Board board, Member member);
	public List<Bookmark> findByMember(Member member);
	public Page<Bookmark> findByMember(Member member, Pageable pageable);
}
