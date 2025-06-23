package com.ssafy.common.filter;

import java.io.IOException;
import java.util.List;

import com.ssafy.auth.exception.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import org.springframework.http.server.PathContainer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssafy.auth.jwt.service.JwtService;
import com.ssafy.auth.jwt.util.JwtUtil;
import com.ssafy.auth.principal.CustomUserDetails;

import io.jsonwebtoken.Claims;
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
	private final JwtService jwtService;
	private final PathPatternParser pathPatternParser;
	private final JwtUtil jwtUtil;
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		List<PathPattern> excludedPatterns = List.of(
				pathPatternParser.parse("/swagger-ui/**"),
				pathPatternParser.parse("/swagger/**"),
				pathPatternParser.parse("/v3/api-docs/**"),
				pathPatternParser.parse("/api/auth/**")
				);
		PathContainer path = PathContainer.parsePath(request.getRequestURI());
		return excludedPatterns.stream().anyMatch(pattern -> pattern.matches(path));
	}
	@Override
	protected void doFilterInternal(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final FilterChain filterChain
	) throws ServletException, IOException {

		String authorizationHeader = getAuthorizationHeader(request);
		log.info("JwtAuthenticationFilter Authorization Header: {}", authorizationHeader);

		String token = jwtUtil.extractToken(authorizationHeader);

		Claims claims;
		try {
			claims = jwtUtil.parse(token);
		} catch (BusinessException e) {
			SecurityContextHolder.clearContext();
			throw e;
		}

		Long id = claims.get(JwtUtil.ID, Long.class);
		
		CustomUserDetails userDetails = new CustomUserDetails(id);
		
		log.info("UserDetails: {}", userDetails);
		log.info("UserDetails authorities: {}", userDetails.getAuthorities());

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities()
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}


	private String getAuthorizationHeader(final HttpServletRequest request) {
		return request.getHeader("Authorization");
	}

	
    	
	
}
