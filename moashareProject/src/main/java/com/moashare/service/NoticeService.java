package com.moashare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moashare.model.Board;
import com.moashare.model.Notice;
import com.moashare.model.Reply;
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

	public List<Notice> getNoticeList() {
		List<Notice> noticeList=noticeRepository.findAll();
		return noticeList;
	}
	
	@Transactional
	public Notice detailView(Long id) {
		Notice notice=noticeRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException("아이디를 찾을 수 없어 글을 볼 수 없습니다.");
				});
		return notice;
	}
	
}
