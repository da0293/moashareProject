package com.moashare.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.moashare.config.auth.PrincipalDetails;
import com.moashare.config.oauth.provider.GoogleUserInfo;
import com.moashare.config.oauth.provider.NaverUserInfo;
import com.moashare.config.oauth.provider.OAuth2UserInfo;
import com.moashare.dao.MemberDAO;
import com.moashare.dto.MemberDTO;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private final MemberDAO dao;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public PrincipalOauth2UserService(MemberDAO dao, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.dao=dao;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		OAuth2UserInfo oAuth2UserInfo=null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글로그인");
			oAuth2UserInfo=new GoogleUserInfo(oauth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버로그인");
			oAuth2UserInfo=new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		}else {
			System.out.println("구글과 네이버만 지원");
		}
		
		String provider=oAuth2UserInfo.getProvider();
		System.out.println("provider : " +provider);
		String id=oAuth2UserInfo.getEmail();
		String randomPw = UUID.randomUUID().toString();
		String pw=bCryptPasswordEncoder.encode(randomPw);
		String nickname=oAuth2UserInfo.getNickname();
		MemberDTO dto = dao.getMemberByEmail(id);
		if(dto==null){ //OAuth로그인 최초
			dto=MemberDTO.builder()
					.id(id)
					.pw(pw)
					.nickname(nickname)
					.provider(provider)
					.build();
			dao.insertOne(dto);
		}else { // 이미 구글로그인으로 회원가입된 회원
			
		}
		return new PrincipalDetails(dto, oauth2User.getAttributes());
	}
}
