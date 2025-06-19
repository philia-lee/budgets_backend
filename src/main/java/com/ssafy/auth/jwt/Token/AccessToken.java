package com.ssafy.auth.jwt.Token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record AccessToken(@JsonValue String value)// 객체를 JSON으로 직렬화(Serialization)할 때 이 value 필드의 값만을 사용하라고 지시
{

    @JsonCreator //JSON을 자바 객체로 역직렬화(Deserialization)할 때 사용할 생성자
    public static AccessToken of(String value) {
        return new AccessToken(value);
    }
}


