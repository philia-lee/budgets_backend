package com.ssafy.auth.jwt.Token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record RefreshToken(@JsonValue String value) {
	 @JsonCreator
	    public static RefreshToken of(String value) {
	        return new RefreshToken(value);
	    }
}
