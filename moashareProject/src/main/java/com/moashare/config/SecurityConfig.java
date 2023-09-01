package com.moashare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.moashare.config.oauth.PrincipalOauth2UserService;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
public class SecurityConfig {
	
	private PrincipalOauth2UserService principalOauth2UserService;
	private FailureHandler failurehandler;
	
	public SecurityConfig(PrincipalOauth2UserService pos, FailureHandler failurehandler) {
		this.principalOauth2UserService=pos;
		this.failurehandler=failurehandler;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable) 
				.authorizeHttpRequests((authorizeRequests) -> { 
					authorizeRequests.requestMatchers("/home").authenticated();
					authorizeRequests.requestMatchers("/admin").hasRole("ADMIN"); 
					authorizeRequests.anyRequest().permitAll();
				})

				.formLogin((formLogin) -> {
					formLogin.loginPage("/login") 
							.loginProcessingUrl("/login")
							.failureHandler(failurehandler)
							.defaultSuccessUrl("/home"); 
							
				})
				.oauth2Login((oauth2Login)->{
					oauth2Login.loginPage("/login")
							.defaultSuccessUrl("/home")
							.userInfoEndpoint()
							.userService(principalOauth2UserService);
				})
				.logout(logout -> logout
                        // 로그아웃 요청을 처리할 URL 설정
                        .logoutUrl("/logout")
                        // 로그아웃 성공 시 리다이렉트할 URL 설정
                        .logoutSuccessUrl("/login")
                        // 로그아웃 핸들러 추가 (세션 무효화 처리)
                        .addLogoutHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            session.invalidate();
                        })
                        // 로그아웃 성공 핸들러 추가 (리다이렉션 처리)
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.sendRedirect("/login"))
                        // 로그아웃 시 쿠키 삭제 설정 (예: "remember-me" 쿠키 삭제)
                        .deleteCookies("JSESSIONID")
                )
				.build();
	}

}
