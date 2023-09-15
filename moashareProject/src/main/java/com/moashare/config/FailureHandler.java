package com.moashare.config;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

@Component
public class FailureHandler implements AuthenticationFailureHandler {
//	private final String url = "/login/fail";
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String errorMsg=null;
		if (exception instanceof AuthenticationServiceException) {
			errorMsg = "존재하지 않는 사용자입니다.";
        }else if(exception instanceof BadCredentialsException){
            errorMsg = "아이디나 비밀번호가 맞지 않습니다.";
        }else if(exception instanceof DisabledException) {
            errorMsg = "계정이 비활성화 되었습니다.관리자에게 문의하세요.";
        }else if(exception instanceof LockedException){
            errorMsg = "이메일이 인증되지 않았습니다.이메일을 확인해 주세요.";
        }else{
            errorMsg = "알수없는 이유로 로그인에 실패하였습니다.관리자에게 문의하세요.";
        }
		
		request.setAttribute("errorMsg", errorMsg);
        request.getRequestDispatcher("/login/fail").forward(request, response);
	}

}
