package com.moashare;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.moashare.model.Board;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootTest
class BaordApiControllerTest {

	private Validator validator;
	
	@BeforeEach
	public void setUpValidator() {
		validator=Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	@Test
	@DisplayName("@NotBlank 타이틀 컬럼 에러 테스트")
	void 게시판title_notblank_테스트() throws Exception {
		// given
		Board board=Board.builder()
				.title("")
				.content("안녕")
				.build();
		// when
		Set<ConstraintViolation<Board>> violations =
				validator.validate(board);
		//then
		 assertThat(violations).isNotEmpty();
	        violations
	                .forEach(error -> {
	                    assertThat(error.getMessage()).isEqualTo("제목은 공백일 수 없습니다");
	                });
	}
	
	@Test
	@DisplayName("@NotBlank content 컬럼 에러 테스트")
	void 게시판content_notblank_테스트() throws Exception {
		// given
		Board board=Board.builder()
				.title("제목")
				.content("")
				.build();
		// when
		Set<ConstraintViolation<Board>> violations =
				validator.validate(board);
		//then
		 assertThat(violations).isNotEmpty();
	        violations
	                .forEach(error -> {
	                    assertThat(error.getMessage()).isEqualTo("내용은 공백일 수 없습니다");
	                });
	}
	
	@DisplayName("유효성 성공")
    @Test
    void 유효성_성공_테스트() {
        // given
		// given
				Board board=Board.builder()
						.title("제목")
						.content("안녕")
						.build();

        // when
		Set<ConstraintViolation<Board>> violations =
				validator.validate(board);

        // then
        assertThat(violations).isEmpty(); // 유효한 경우
    }
}
