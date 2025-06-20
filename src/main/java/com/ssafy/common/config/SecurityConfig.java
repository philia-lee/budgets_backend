package com.ssafy.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // <<-- 이 임포트 추가!
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // <<-- 이 임포트 추가!

import com.ssafy.auth.jwt.service.JwtService;
import com.ssafy.common.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration // 이 클래스가 스프링 설정 클래스임을 나타냅니다.
@EnableWebSecurity // 스프링 시큐리티 설정을 활성화합니다.
@RequiredArgsConstructor // Lombok 어노테이션으로 final 필드에 대한 생성자를 자동 생성하여 의존성 주입을 돕습니다.
public class SecurityConfig {

    private final JwtService jwtSevice; // JWT 관련 서비스 주입

    // 비밀번호 인코더 빈 등록 (BCrypt 방식으로 비밀번호를 암호화합니다)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈 등록 (인증 처리를 담당하는 핵심 인터페이스)
    // 스프링 시큐리티 6부터는 AuthenticationConfiguration을 통해 주입받아야 합니다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 보안 필터 체인 설정 (가장 중요!)
    // HTTP 요청에 대한 보안 규칙을 정의합니다.
    @Bean
    public SecurityFilterChain securitFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF (Cross-Site Request Forgery) 보호 비활성화:
            // REST API 서버에서는 세션 기반이 아닌 토큰 기반(JWT) 인증을 주로 사용하므로,
            // CSRF 공격에 대한 직접적인 노출이 적습니다. 따라서 CSRF 보호를 비활성화합니다.
            // 이 설정을 추가하지 않으면 POST/PUT/DELETE 요청 시 403 에러가 발생할 수 있습니다.
            .csrf(AbstractHttpConfigurer::disable) // <<-- 이 부분 추가

            // 2. HTTP 요청에 대한 인가(Authorization) 규칙 설정:
            // 어떤 경로의 요청을 허용할지, 어떤 요청에 인증이 필요한지 등을 정의합니다.
            .authorizeHttpRequests(authorize -> authorize
                // Swagger UI 및 OpenAPI 문서 관련 경로들은 인증 없이 **모두 접근 허용**합니다.
                // - /swagger-ui/** : Swagger UI 웹 페이지와 그에 필요한 CSS/JS 파일들
                // - /v3/api-docs/** : OpenAPI 3.0 명세 JSON 파일 (Swagger UI가 이 파일을 읽어서 API 목록을 만듭니다)
                // - /swagger-resources/**, /webjars/** : Swagger UI 작동에 필요한 추가 리소스들
                .requestMatchers(
                    new AntPathRequestMatcher("/swagger-ui/**"),
                    new AntPathRequestMatcher("/v3/api-docs/**"),
                    new AntPathRequestMatcher("/swagger-resources/**"),
                    new AntPathRequestMatcher("/webjars/**")
                ).permitAll()
                // 회원가입 및 로그인 API 경로는 인증 없이 **모두 접근 허용**합니다.
                // 이 부분을 추가하는 것이 403 에러 해결의 핵심입니다!
                // 회원가입이나 로그인은 아직 인증되지 않은 사용자도 접근할 수 있어야 하기 때문입니다.
                .requestMatchers(
                    new AntPathRequestMatcher("/api/auth/register"), // 회원가입 엔드포인트 경로
                    new AntPathRequestMatcher("/api/auth/login")     // 로그인 엔드포인트 경로 (로그인 기능이 있다면)
                ).permitAll() // 위에 명시된 경로들은 모두 접근 허용
                // 위에 명시된 경로들을 제외한 **나머지 모든 요청**은 **인증이 필요**합니다.
                // 즉, JWT 토큰이 유효해야만 접근할 수 있습니다.
                .anyRequest().authenticated()
            );

        // JWT 인증 필터 추가:
        // 사용자 이름/비밀번호 인증 필터(UsernamePasswordAuthenticationFilter) 이전에
        // 우리가 만든 JwtAuthenticationFilter를 추가합니다.
        // 이 필터는 HTTP 요청 헤더에서 JWT 토큰을 추출하여 유효성을 검증하고, 유효하면 사용자를 인증 처리합니다.
        // 참고: permitAll() 처리된 경로는 이 필터가 작동하기 전에 이미 허용되므로,
        // 회원가입/로그인 같은 공개 API에는 토큰이 없어도 접근할 수 있습니다.
        http.addFilterBefore(new JwtAuthenticationFilter(jwtSevice), UsernamePasswordAuthenticationFilter.class);

        // 빌드된 HttpSecurity 객체를 반환하여 보안 필터 체인을 완성합니다.
        return http.build();
    }
}