package com.moashare.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
public class CacheTestController {

	@GetMapping("/cache")
//	@Cacheable(cacheNames = "cache")
	public String CacheTestDto() {
		CacheTestDto cache = new CacheTestDto("name", 10);
		System.out.println("데이터를 만들었음"); // 새로고침을 해도 한번만 출력되는 것을 확인함 
		return "home/test";
	}
	
	@AllArgsConstructor
	@Getter
	private class CacheTestDto{
		private String name;
		private int age;
	}
}
