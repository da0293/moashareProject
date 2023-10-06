package com.moashare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching	// 캐시 활성화
@SpringBootApplication
public class MoashareProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoashareProjectApplication.class, args);
	}

}
