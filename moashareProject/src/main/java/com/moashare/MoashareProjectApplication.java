package com.moashare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@EnableCaching	// 캐시 활성화
@SpringBootApplication
public class MoashareProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoashareProjectApplication.class, args);
	}

}
