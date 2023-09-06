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
import com.moashare.enumpackage.AuthType;
import com.moashare.model.Member;
import com.moashare.repository.MemberRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public PrincipalOauth2UserService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.memberRepository=memberRepository;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		OAuth2UserInfo oAuth2UserInfo=null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			oAuth2UserInfo=new GoogleUserInfo(oauth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			oAuth2UserInfo=new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		}else {
		}
		
		String provider=oAuth2UserInfo.getProvider();
		String email=oAuth2UserInfo.getEmail();
		String randomPw = UUID.randomUUID().toString();
		String password=bCryptPasswordEncoder.encode(randomPw);
		String nickname=oAuth2UserInfo.getNickname();
		Member memberEntity=memberRepository.findByEmail(email);
		String auth="ROLE_USER";
		if(memberEntity==null){ //OAuth로그인 최초
			memberEntity = Member.builder()
					  .email(email)
					  .password(password)
					  .nickname(nickname)
					  .provider(provider)
					  .auth(AuthType.ROLE_USER)
					  .build();
			memberRepository.save(memberEntity);
		}
		return new PrincipalDetails(memberEntity, oauth2User.getAttributes());
		
	}
}
