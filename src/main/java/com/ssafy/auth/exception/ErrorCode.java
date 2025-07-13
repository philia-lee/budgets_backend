// exception/ErrorCode.java
package com.ssafy.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus; // HTTP 상태 코드 임포트

/**
 * 애플리케이션에서 발생할 수 있는 모든 오류 코드를 정의하는 enum 클래스입니다.
 * 각 오류 코드는 HTTP 상태 코드와 클라이언트에게 전달할 메시지를 포함합니다.
 */
@Getter // 각 ErrorCode 항목의 필드 값을 가져올 수 있도록 Getter를 추가합니다.
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.
public enum ErrorCode {

	 // --- 토큰 관련 에러 코드 ---
    MISSING_TOKEN(401_001, HttpStatus.UNAUTHORIZED, "토큰이 누락되었습니다."), // Authorization 헤더에 토큰이 없을 때
    ILLEGAL_TOKEN(401_002, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."), // 토큰 형식이 잘못되었거나 서명이 유효하지 않을 때
    EXPIRED_TOKEN(401_003, HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."), // 토큰 유효 기간이 지났을 때
    UNSUPPORTED_TOKEN(401_004, HttpStatus.UNAUTHORIZED, "지원되지 않는 토큰 형식입니다."), // JWT가 아닌 다른 형식의 토큰일 때
    MALFORMED_TOKEN(401_005, HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."), // JWT가 올바른 JSON 형식을 가지지 않을 때
    UNKNOWN_TOKEN(401_006, HttpStatus.UNAUTHORIZED, "알 수 없는 토큰 오류입니다."), // 그 외 JWT 관련 일반 오류
    ACCESS_DENIED(403_001, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    // --- 로그인/회원가입/사용자 관련 에러 코드 ---
    ALREADY_EXIST_EMAIL(409_001, HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."), // 회원가입 시 이메일 중복
    ALREADY_EXIST_NICKNAME(409_002, HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."), // 회원가입 시 닉네임 중복
    ILLEGAL_SIGN_IN(401_007, HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."), // 로그인 실패
    USER_NOT_FOUND(404_001, HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."), // 특정 사용자를 찾지 못했을 때 (예: 이메일 찾기)
    
    // --- 기타 일반적인 서버 에러 ---
    INTERNAL_SERVER_ERROR(500_001, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."), // 예상치 못한 서버 오류
    INVALID_INPUT_VALUE(400_001, HttpStatus.BAD_REQUEST, "유효하지 않은 입력 값입니다."), // 요청 파라미터 유효성 검사 실패
    ILLEGAL_ARGUMENT(400_002, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    MISSING_REQUEST_PARAMETER(400_002, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다.");
	
	
    // 각 에러 코드가 가질 필드: HTTP 상태 코드와 클라이언트에게 보여줄 메시지
	private final Integer code; //에러 코드 번호
    private final HttpStatus httpStatus; // 에러 상태
    private final String message; // 클라이언트에게 전달할 오류 메시지
}
