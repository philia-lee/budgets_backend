package com.ssafy.auth.exception.exception;

import com.ssafy.auth.exception.ErrorCode; // ErrorCode 임포트
import lombok.Getter; // Lombok의 Getter 어노테이션

/**
 * 애플리케이션의 비즈니스 로직에서 발생하는 특정 오류를 나타내는 커스텀 예외 클래스입니다.
 * HTTP 응답 코드와 메시지를 포함하는 ErrorCode를 사용하여 오류의 상세 정보를 제공합니다.
 */
@Getter // 예외가 발생했을 때 ErrorCode를 외부에서 가져올 수 있도록 Getter를 추가합니다.
public class BusinessException extends RuntimeException { // RuntimeException을 상속하여 언체크 예외로 만듭니다.

    private final ErrorCode errorCode; // 이 비즈니스 예외와 연결된 ErrorCode 객체

    /**
     * ErrorCode를 인자로 받아 BussinessException을 생성합니다.
     * ErrorCode에 정의된 메시지를 예외 메시지로 사용합니다.
     *
     * @param errorCode 발생한 비즈니스 오류에 해당하는 ErrorCode
     */
    public BusinessException(ErrorCode errorCode) {
        // RuntimeException의 생성자에 ErrorCode의 메시지를 전달하여 예외 메시지를 설정합니다.
        super(errorCode.getMessage());
        this.errorCode = errorCode; // 현재 예외 객체에 ErrorCode를 저장합니다.
    }

    /**
     * ErrorCode와 추가적인 상세 메시지를 인자로 받아 BussinessException을 생성합니다.
     *
     * @param errorCode 발생한 비즈니스 오류에 해당하는 ErrorCode
     * @param message 예외에 대한 추가적인 상세 메시지
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message); // 추가적인 상세 메시지를 예외 메시지로 설정합니다.
        this.errorCode = errorCode;
    }
}
