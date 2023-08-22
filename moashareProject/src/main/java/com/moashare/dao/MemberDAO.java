package com.moashare.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.moashare.dto.MemberDTO;

@Mapper
@Repository
public interface MemberDAO {

	MemberDTO getMemberByEmail(String emailId);
	MemberDTO getMemberByNickname(String nickname);

}
