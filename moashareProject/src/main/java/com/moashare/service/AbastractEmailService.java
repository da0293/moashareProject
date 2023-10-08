package com.moashare.service;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public abstract class AbastractEmailService {
	protected JavaMailSender emailSender;
    protected SpringTemplateEngine templateEngine;
    protected String authNum;

    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailForm = createEmailForm(toEmail);
        emailSender.send(emailForm);
        return authNum;
    }

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
