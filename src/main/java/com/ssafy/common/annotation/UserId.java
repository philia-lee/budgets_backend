package com.ssafy.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 이 어노테이션은 메서드의 파라미터에만 붙일 수 있습니다.
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 이 어노테이션 정보가 유지되어야 합니다.
public @interface UserId {

}