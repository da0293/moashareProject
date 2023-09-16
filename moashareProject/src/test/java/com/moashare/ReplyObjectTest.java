package com.moashare;

import org.junit.jupiter.api.Test;

import com.moashare.model.Reply;

public class ReplyObjectTest {
	
	@Test
	public void toStringTest() {
		Reply reply=Reply.builder()
				.member(null)
				.board(null)
				.rcontent("고마워")
				.build();
		
		System.out.println(reply); 
	}
}
