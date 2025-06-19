package com.ssafy.common.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.auth.jwt.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
//	private final JwtService jwtService;
	
    @Override // <--- 이 어노테이션이 붙어있어야 합니다.
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. 요청 헤더에서 JWT 토큰 추출
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail; // 또는 userId

//        // Authorization 헤더가 없거나 "Bearer "로 시작하지 않으면 다음 필터로 넘깁니다.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        
        filterChain.doFilter(request, response);
    }
	
	
}
