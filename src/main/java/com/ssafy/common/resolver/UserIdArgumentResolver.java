package com.ssafy.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ssafy.auth.principal.CustomUserDetails;
import com.ssafy.common.annotation.UserId;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		// 1. 파라미터에 @UserId 어노테이션이 붙어 있는지 확인
		// 2. 파라미터 타입이 Long.class (java.lang.Long)와 정확히 일치하는지 확인
		return parameter.hasParameterAnnotation(UserId.class)
	            && parameter.getParameterType().equals(Long.class);
	}

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        // SecurityContextHolder에서 현재 인증된 사용자의 Principal 객체를 가져옵니다.
        // 그리고 이를 CustomUserDetails 타입으로 캐스팅합니다.
    	log.info("UserIdArgumentResolver: resolveArgument 호출됨.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.warn("UserIdArgumentResolver: Authentication 객체가 null입니다. 요청이 인증되지 않았거나 Authentication이 SecurityContextHolder에 설정되지 않았습니다.");
            return null; // 이 경우 "Required field not provided" 오류가 발생할 수 있음
        }
        if (!authentication.isAuthenticated()) {
            log.warn("UserIdArgumentResolver: Authentication.isAuthenticated()가 false입니다. 사용자 ID를 주입할 수 없습니다.");
            return null;
        }

        Object principal = authentication.getPrincipal();
        log.info("UserIdArgumentResolver: Principal 객체 타입: {}", principal != null ? principal.getClass().getName() : "null");

        if (!(principal instanceof CustomUserDetails)) {
            log.error("UserIdArgumentResolver: Principal이 CustomUserDetails 타입이 아닙니다. 실제 타입: {}", principal != null ? principal.getClass().getName() : "null");
            throw new IllegalStateException("인증된 사용자 정보(Principal)가 CustomUserDetails 타입이 아닙니다.");
        }

        CustomUserDetails detail = (CustomUserDetails) principal;

        if (detail.getId() == null) {
            log.warn("UserIdArgumentResolver: CustomUserDetails의 ID가 null입니다. 사용자 ID를 주입할 수 없습니다.");
            return null;
        }

        log.info("UserIdArgumentResolver: 사용자 ID를 성공적으로 확인했습니다: {}", detail.getId());
        return detail.getId(); // 이제 CustomUserDetails.getId()는 Long을 반환합니다.

    }
}