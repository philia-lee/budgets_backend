package com.ssafy.common.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.auth.exception.ErrorCode;
import com.ssafy.auth.exception.dto.ExceptionResponse;
import com.ssafy.auth.exception.exception.BusinessException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtExceptionFilter  extends OncePerRequestFilter  {
	private final ObjectMapper objectMapper;
	
	protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException e) {
            setErrorResponse(response, e, ErrorCode.ACCESS_DENIED);
        } catch (BusinessException e) {
            setErrorResponse(response, e, e.getErrorCode());
        }  catch (Exception e) {
            e.printStackTrace();
            setErrorResponse(response, e, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void setErrorResponse(HttpServletResponse response, Exception e, ErrorCode errorCode) throws IOException {
        
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(ExceptionResponse.of(errorCode)));
    }

}
