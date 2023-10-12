package com.moashare.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moashare.model.Notice;
import com.moashare.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	@Transactional // 함수 종료 시 자동 commit
	 public void saveNotice(Notice notice) {
		noticeRepository.save(notice);
	 }
	
}
