package com.moashare.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.dto.BoardDTO;
import com.moashare.dto.HotBoardDTO;
import com.moashare.model.Board;
import com.moashare.model.Bookmark;
import com.moashare.model.HotBoard;
import com.moashare.service.BoardService;
import com.moashare.service.MemberService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class HomeController {
	
	private BoardService boardService;
	private MemberService memberService;
	
	public HomeController(BoardService boardService,MemberService memberService) {
		this.boardService=boardService;
		this.memberService=memberService;
	}
	
	
	@GetMapping("home")
	public String member(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model,@PageableDefault(page=0, size = 12, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
//		List<Board> hotBoardList=boardService.hotBoardList();// 인기글 가져오기 
		Page<HotBoardDTO> hotBoardList= null;
		hotBoardList=boardService.getHotBoardList(pageable);// 인기글 가져오기 
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("hotBoardList", hotBoardList);
		return "home/homepage";
	}
	
	@GetMapping("/profile")
	public String updateProfile(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		model.addAttribute("nickname", principalDetails.getMember().getNickname());
		model.addAttribute("email", principalDetails.getMember().getEmail());
		model.addAttribute("id", principalDetails.getMember().getId());
		return "home/profileForm";
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
