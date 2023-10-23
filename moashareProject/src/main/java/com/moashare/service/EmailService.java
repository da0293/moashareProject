package com.moashare.service;

import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService extends AbastractEmailService {
	
	// 의존성 주입
	public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        super(emailSender, templateEngine);
    }
	
	// 새로운 이메일 기능에 맞는 인증 코드 생성 로직 구현
	@Override
	protected void createCode() {
		Random random = new Random();
		StringBuffer key = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(3);

			switch (index) {
			case 0:
				key.append((char) ((int) random.nextInt(26) + 97));
				break;
			case 1:
				key.append((char) ((int) random.nextInt(26) + 65));
				break;
			case 2:
				key.append(random.nextInt(9));
				break;
			}
		}
		authNum = key.toString();

	}

	// 새로운 이메일 기능에 맞는 이메일 제멕 설정 
	@Override
	protected String getEmailTitle() {
		String title = "MOASHARE 이메일 인증 번호입니다."; //제목
		return title;
	}

	// 새로운 이메일 기능에 맞는 타임리프 context 설정 
	@Override
	protected String setContext(String code) {
		Context context = new Context();
		context.setVariable("code", code);
		return templateEngine.process("mail", context); // mail.html
	}
}