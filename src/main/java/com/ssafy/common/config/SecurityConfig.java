package com.ssafy.common.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.auth.jwt.service.JwtService;
import com.ssafy.common.filter.JwtAuthenticationFilter; // JwtAuthenticationFilter 임포트
import com.ssafy.common.filter.JwtExceptionFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtSevice;
    // JwtAuthenticationFilter를 주입받도록 final 필드로 선언합니다.
    private final JwtAuthenticationFilter jwtAuthenticationFilter; 
    private final JwtExceptionFilter jwtExceptionFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securitFilterChain(HttpSecurity http) throws Exception {
        http
        .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    new AntPathRequestMatcher("/swagger-ui/**"),
                    new AntPathRequestMatcher("/v3/api-docs/**"),
                    new AntPathRequestMatcher("/swagger-resources/**")
                ).permitAll()
                .requestMatchers(
                    new AntPathRequestMatcher("/api/auth/register"),
                    new AntPathRequestMatcher("/api/auth/login")
                ).permitAll()
                .anyRequest().authenticated()
            );
        http
        .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
        

        return http.build();
    }
    
 // CORS 설정을 위한 CorsConfigurationSource 빈 정의
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // cors 허용할 주소
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173","http://localhost:5174")); // Vue 개발 서버 주소
        
        // 허용할 HTTP 메서드 (GET, POST, PUT, DELETE 등)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // 허용할 헤더 (Authorization, Content-Type 등)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // 자격 증명(쿠키, HTTP 인증, 클라이언트 측 SSL 인증서)을 포함한 요청 허용 여부
        configuration.setAllowCredentials(true);
        
        // Preflight 요청 결과를 캐시할 시간 (초)
        configuration.setMaxAge(3600L); // 1시간 동안 Preflight 결과를 캐시

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 위에서 정의한 CORS 설정을 적용합니다.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    
}