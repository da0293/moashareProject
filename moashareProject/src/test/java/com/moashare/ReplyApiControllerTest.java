package com.moashare;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.moashare.model.Board;
import com.moashare.model.Reply;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootTest
class ReplyApiControllerTest {

	private Validator validator;

	@BeforeEach
	public void setUpValidator() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	@DisplayName("@NotBlank 타이틀 컬럼 에러 테스트")
	void 댓글rcontent_notblank_테스트() throws Exception {
		// given
		Reply reply = Reply.builder().rcontent("").build();

		// when
		Set<ConstraintViolation<Reply>> violations = validator.validate(reply);
		
		// then
		assertThat(violations).isNotEmpty();
		violations.forEach(error -> {
			assertThat(error.getMessage()).isEqualTo("댓글은 공백일 수 없습니다");
		});
	}

	@DisplayName("유효성 성공")
	@Test
	void 유효성_성공_테스트() {
		// given
		Reply reply = Reply.builder().rcontent("안녕").build();

		// when
		Set<ConstraintViolation<Reply>> violations = validator.validate(reply);
		
		// then
		assertThat(violations).isEmpty(); // 유효한 경우
	}
}
