package com.moashare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moashare.model.Notice;

public interface NoticeRepository  extends JpaRepository<Notice, Long>  {

}
