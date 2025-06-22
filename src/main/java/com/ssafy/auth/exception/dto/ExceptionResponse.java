package com.ssafy.auth.exception.dto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.auth.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)

public class ExceptionResponse {
	private final Integer code;
	private final HttpStatus httpStatus;
	private final String message;
	private final List<FieldError> errors;

	@Builder
	private ExceptionResponse(final Integer code, final HttpStatus httpStatus, final String message, final List<FieldError> errors) {
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
		this.errors = errors;
	}

	public static ExceptionResponse of(final ErrorCode errorCode) {
		return ExceptionResponse.builder()
				.code(errorCode.getCode())
				.httpStatus(errorCode.getHttpStatus())
				.message(errorCode.getMessage())
				.build();
	}

	public static ExceptionResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        return ExceptionResponse.builder()
                .httpStatus(errorCode.getHttpStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(FieldError.of(bindingResult))
                .build();
	}
	
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

}
