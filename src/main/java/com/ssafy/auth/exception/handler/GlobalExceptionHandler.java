package com.ssafy.auth.exception.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.auth.exception.ErrorCode;
import com.ssafy.auth.exception.dto.ExceptionResponse;
import com.ssafy.auth.exception.exception.BusinessException;


@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		final ExceptionResponse response = ExceptionResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER, e.getBindingResult());
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionResponse> bussinessExceptionHandler(BusinessException e) {
		
		ExceptionResponse response = ExceptionResponse.of(e.getErrorCode());
		return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
	}
	
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgument(IllegalArgumentException e) {
		
		

        final ExceptionResponse response = ExceptionResponse.of(ErrorCode.ILLEGAL_ARGUMENT);
        return ResponseEntity.badRequest().body(response);
    }




}
