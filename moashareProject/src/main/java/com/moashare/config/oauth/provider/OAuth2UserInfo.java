package com.moashare.config.oauth.provider;

public interface OAuth2UserInfo {
	String getProvider();
	String getEmail();
	String getNickname();
}
