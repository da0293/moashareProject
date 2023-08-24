package com.moashare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 사이트 위변조 요청 방지
				.authorizeHttpRequests((authorizeRequests) -> { // 특정 URL에 대한 권한 설정.
					authorizeRequests.requestMatchers("/home/**").authenticated();
					authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN"); 
					authorizeRequests.anyRequest().permitAll();
				})

				.formLogin((formLogin) -> {
					formLogin.loginPage("/login") // 권한이 필요한 요청은 해당 url로 리다이렉트
							.loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 해준다.
							.defaultSuccessUrl("/"); // 로그인 성공시 /주소로 이동

				}).build();
	}

}
