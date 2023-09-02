package com.moashare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moashare.config.auth.PrincipalDetails;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class HomeController {
	
	
	@GetMapping("/home")
	public String member(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		model.addAttribute("nickname", principalDetails.getDto().getNickname());
//		System.out.println("이름 : " +principalDetails.getDto());
		return "home/homepage";
	}
	
	
	// 협업공간이름 검색
	@GetMapping("/search") 	
	public String searchClubName(@RequestParam(value = "searchValue")String searchValue, Model model,@AuthenticationPrincipal PrincipalDetails userDetails ) {
		// 해당 단어가 속하는지  
//		log.info(searchValue);
//		List<ClubDTO> clubList = cs.searchKeyword(searchValue, userDetails.getMdto().getEmail() );
//		model.addAttribute("clubList", clubList);
//		log.info( "search" + searchValue);
//		model.addAttribute("myClub", ClubUtil.getClub(userDetails)); 
		return "home/search";
        
	}
	
}
