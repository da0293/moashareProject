package com.moashare.service;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbastractEmailService {
	
	protected JavaMailSender emailSender;
    protected SpringTemplateEngine templateEngine;
    protected String authNum;
    
    public AbastractEmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
		this.emailSender=emailSender;
		this.templateEngine=templateEngine;
	}
    
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailForm = createEmailForm(toEmail); //메일전송에 필요한 정보 설정
        emailSender.send(emailForm); //실제 메일 전송
        return authNum; //인증 코드 반환  
    }

    //추상 메소드(abstract method)란 자식 클래스에서 반드시 오버라이딩해야만 사용할 수 있는 메소드
    protected abstract void createCode(); 
    protected abstract String getEmailTitle();  
    protected abstract String setContext(String code);

    private MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        createCode();
        String title = getEmailTitle();
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(title);
        message.setText(setContext(authNum), "utf-8", "html");
        return message;
    }

	
}
