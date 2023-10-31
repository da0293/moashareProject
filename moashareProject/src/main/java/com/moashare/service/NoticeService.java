package com.moashare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
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
	private final MessageSource messageSource;

	@Transactional
	 public void saveNotice(Notice notice) {
		noticeRepository.save(notice);
	 }

	@Transactional(readOnly = true)
	public List<Notice> getNoticeList() {
		List<Notice> noticeList=noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return noticeList;
	}
	
	@Transactional
	public Notice detailView(Long id) {
		Notice notice=noticeRepository.findById(id)
				.orElseThrow(() -> {
					return new IllegalArgumentException(messageSource.getMessage("noticeNotFound ", null, LocaleContextHolder.getLocale()));
				});
		return notice;
	}

	@Transactional
	public void deleteNotice(Long id) {
		noticeRepository.deleteById(id);
	}
	
}
