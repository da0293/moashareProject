package com.moashare.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 어디서든 exception발생시 여기로 
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	public String handleArgumentException(Exception e) {
		return "<h1>" + e.getMessage() +"</h1>";
	}
//	@ControllerAdvice를 이용한 전역 에러 핸들링, 혹은 @Controller단에서의 지역 에러 핸들링을 사용하면 된다.
//	MethodArgumentNotValidException에 대한 @ExceptionHandler 어노테이션을 지정하여 커스텀 에러 핸들링
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<java.util.Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
	
//	ResponseEntity 값으로, error가 난 field 값과, 에러 메시지를 Map 형태로 만들어서, Response로 넣어주었다. 
//	이때 Map으로 선언하여 forEach를 한 이유는 @Valid를 사용할 때, 해당 객체에서 valid에 실패한 내용을 모두 리턴해주기 때문에, 
//	모든 error 값을 수용하기 위해서이다.
}
