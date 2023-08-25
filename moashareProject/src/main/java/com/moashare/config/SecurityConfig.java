package com.moashare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.moashare.config.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨.
public class SecurityConfig {
	
	private PrincipalOauth2UserService principalOauth2UserService;
	
	public SecurityConfig(PrincipalOauth2UserService pos) {
		this.principalOauth2UserService=pos;
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
							.failureUrl("/login?error")
							.defaultSuccessUrl("/home"); 
							
				})
				.oauth2Login((oauth2Login)->{
					oauth2Login.loginPage("/login")
							.failureUrl("/login?error")
							.defaultSuccessUrl("/home")
							.userInfoEndpoint()
							.userService(principalOauth2UserService);
				})
				.build();
	}
}
