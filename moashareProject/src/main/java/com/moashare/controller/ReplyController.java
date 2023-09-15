package com.moashare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.moashare.model.Board;
import com.moashare.repository.BoardRepository;

@RestController
public class ReplyController {
	@Autowired
	private BoardRepository boardRepository; // RestController에선 private final 불가
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable Long id) {
		return boardRepository.findById(id).get(); 
		// 무한참조된다. -> Board모델의 replys가 문제, jackson 라이브러리 (오브젝트를 json으로 리턴) -> 모델의 getter호출
		// get reply에서 문제가 일어나는데 reply클래스에서 get id, rcontent...하다가 다시 get board
		// 그러면 다시 board클래스로 가서 get id, get title...이런 식으로 무한참조된다.
		// 방법 : reply갔을 때 board호출 안되도록 무시, Board모델에 reply컬럼 위에 표시 : @JsonIgonreProperties 사용
	}
}
