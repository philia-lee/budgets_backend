package com.ssafy.auth.jwt.util;

// 필요한 Java 유틸리티 및 암호화 관련 클래스 임포트
import java.util.Date; // 날짜 및 시간 처리를 위한 Date 클래스
import java.util.Map; // 클레임(Claims)을 위한 Map 인터페이스
import javax.crypto.SecretKey; // JWT 서명에 사용할 비밀 키 (HMAC 알고리즘용)

// Spring 프레임워크 관련 클래스 임포트
import org.springframework.beans.factory.annotation.Value; // 설정 파일 값 주입을 위한 어노테이션
import org.springframework.http.HttpStatus; // HTTP 상태 코드 (직접 사용되지는 않지만, ErrorCode에서 사용)
import org.springframework.stereotype.Component; // 스프링 빈으로 등록하기 위한 어노테이션

// 프로젝트에서 정의된 예외 및 DTO 임포트
import com.ssafy.auth.exception.ErrorCode; // 사용자 정의 에러 코드 Enum
import com.ssafy.auth.exception.exception.BussinessException; // 사용자 정의 비즈니스 예외
import com.ssafy.auth.jwt.Token.AccessToken; // AccessToken Record/DTO
import com.ssafy.auth.jwt.Token.RefreshToken; // RefreshToken Record/DTO

// JJWT(Java JWT) 라이브러리 관련 클래스 임포트
import io.jsonwebtoken.*; // Jws, Claims, JwtException, ExpiredJwtException 등 다양한 JWT 관련 클래스 포함
import io.jsonwebtoken.io.Decoders; // Base64 디코딩 유틸리티
import io.jsonwebtoken.security.Keys; // 암호화 키 생성 유틸리티 (io.jsonwebtoken.security 패키지)

/**
 * JWT(JSON Web Token) 생성, 파싱, 유효성 검증을 담당하는 유틸리티 클래스입니다.
 * 이 클래스는 JWT 라이브러리(JJWT)의 저수준 기능을 활용하여 토큰 관련 핵심 로직을 제공합니다.
 * JWT의 비밀 키, 만료 기간 등을 외부 설정 파일로부터 주입받아 사용합니다.
 */
@Component // 이 클래스를 스프링 컴포넌트로 등록하여 의존성 주입이 가능하게 합니다.
public class JwtUtil {

    // --- JWT 관련 상수 정의 ---
    public static final String EMAIL = "email"; // 클레임 키: 사용자 이메일
    public static final String TYPE = "type";   // 클레임 키: 토큰 타입 (access/refresh)
    public static final String ID = "id";       // 클레임 키: 사용자 ID (식별자)
    public static final String NAME = "name";   // 클레임 키: 사용자 이름 또는 닉네임
    public static final String BEARER = "Bearer "; // Authorization 헤더에 사용되는 접두사
    public static final String ACCESS = "access"; // 토큰 타입: Access Token
    public static final String REFRESH = "refresh"; // 토큰 타입: Refresh Token
    // public static final String ROLE = "role"; // 역할 클레임 (필요시 사용)

    // JWT 서명에 사용될 비밀 키 (HMAC 알고리즘용 SecretKey 타입)
    private final SecretKey key;
    // Access Token의 유효 기간 (밀리초 단위)
    private final Long accessTokenExpirePeriod;
    // Refresh Token의 유효 기간 (밀리초 단위)
    private final Long refreshTokenExpirePeriod;

    /**
     * JwtUtil 클래스의 생성자입니다.
     * JWT 생성 및 파싱에 필요한 비밀 키와 토큰 만료 기간을 외부 설정 파일로부터 주입받습니다.
     * @Value 어노테이션을 통해 application.properties 또는 application.yml의 값을 주입받습니다.
     *
     * @param secret Base64 인코딩된 JWT 비밀 문자열 (예: ${jwt.secret})
     * @param accessTokenExpirePeriod Access Token의 만료 기간 (예: ${jwt.access-token-expire-period})
     * @param refreshTokenExpirePeriod Refresh Token의 만료 기간 (예: ${jwt.refresh-token-expire-period})
     */
    protected JwtUtil( // protected 접근 제어자는 스프링에서 빈으로 주입받을 때 문제없습니다.
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expire-period}") Long accessTokenExpirePeriod,
            @Value("${jwt.refresh-token-expire-period}") Long refreshTokenExpirePeriod
    ) {
        // 주입받은 Base64 인코딩된 비밀 문자열을 디코딩하여 SecretKey 객체를 생성합니다.
        // Keys.hmacShaKeyFor는 HMAC 알고리즘에 사용할 키를 생성합니다.
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpirePeriod = accessTokenExpirePeriod;
        this.refreshTokenExpirePeriod = refreshTokenExpirePeriod;
    }

    /**
     * Authorization 헤더에서 "Bearer " 접두사를 제거하고 실제 JWT 토큰 문자열을 추출합니다.
     *
     * @param authorizationHeader 전체 Authorization 헤더 문자열 (예: "Bearer eyJ...")
     * @return 추출된 JWT 토큰 문자열
     * @throws BussinessException 헤더가 null이거나 "Bearer "로 시작하지 않을 경우
     */
    public String extractToken(final String authorizationHeader) {
        // Authorization 헤더가 null이거나 "Bearer "로 시작하지 않으면,
        // 토큰이 누락되었음을 알리는 BussinessException을 발생시킵니다.
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            throw new BussinessException(ErrorCode.MISSING_TOKEN);
        }
        // "Bearer " 접두사(7글자)를 제거한 나머지 문자열, 즉 실제 JWT 토큰을 반환합니다.
        return authorizationHeader.substring(7);
    }

    /**
     * AccessToken 객체로부터 JWT 토큰의 만료일을 가져옵니다.
     * @param accessToken 만료일을 확인할 AccessToken 객체
     * @return 토큰의 만료일 (Date 객체)
     */
    public Date getExpiration(AccessToken accessToken) {
        return getExpiration(accessToken.value()); // AccessToken의 value() 메서드로 토큰 문자열 추출
    }

    /**
     * RefreshToken 객체로부터 JWT 토큰의 만료일을 가져옵니다.
     * @param refreshToken 만료일을 확인할 RefreshToken 객체
     * @return 토큰의 만료일 (Date 객체)
     */
    public Date getExpiration(RefreshToken refreshToken) {
        return getExpiration(refreshToken.value()); // RefreshToken의 value() 메서드로 토큰 문자열 추출
    }

    /**
     * JWT 토큰 문자열로부터 만료일을 추출합니다. (내부 사용)
     * @param token 만료일을 추출할 JWT 문자열
     * @return 토큰의 만료일 (Date 객체)
     */
    private Date getExpiration(String token) {
        return parse(token).getExpiration(); // 토큰을 파싱하여 Claims에서 만료일(Expiration)을 가져옵니다.
    }

    /**
     * AccessToken 객체로부터 JWT 토큰의 타입 클레임(TYPE)을 가져옵니다.
     * @param accessToken 타입을 확인할 AccessToken 객체
     * @return 토큰의 타입 (예: "access")
     */
    public String getType(AccessToken accessToken) {
        return getType(accessToken.value()); // AccessToken의 value() 메서드로 토큰 문자열 추출
    }

    /**
     * RefreshToken 객체로부터 JWT 토큰의 타입 클레임(TYPE)을 가져옵니다.
     * @param refreshToken 타입을 확인할 RefreshToken 객체
     * @return 토큰의 타입 (예: "refresh")
     */
    public String getType(RefreshToken refreshToken) {
        return getType(refreshToken.value()); // RefreshToken의 value() 메서드로 토큰 문자열 추출
    }

    /**
     * JWT 토큰 문자열로부터 타입 클레임(TYPE)을 추출합니다. (내부 사용)
     * @param token 타입을 추출할 JWT 문자열
     * @return 토큰의 타입 (예: "access", "refresh")
     */
    private String getType(String token) {
        // 토큰을 파싱하여 Claims에서 TYPE 클레임(예: "access" 또는 "refresh")을 String 타입으로 가져옵니다.
        return parse(token).get(TYPE, String.class);
    }

    /**
     * AccessToken 객체로부터 JWT 클레임(Payload)을 파싱합니다.
     * @param accessToken 파싱할 AccessToken 객체
     * @return 파싱된 JWT 클레임(Claims) 객체
     */
    public Claims parse(AccessToken accessToken) {
        return parse(accessToken.value()); // AccessToken의 value() 메서드로 토큰 문자열 추출
    }

    /**
     * RefreshToken 객체로부터 JWT 클레임(Payload)을 파싱합니다.
     * @param refreshToken 파싱할 RefreshToken 객체
     * @return 파싱된 JWT 클레임(Claims) 객체
     */
    public Claims parse(RefreshToken refreshToken) {
        return parse(refreshToken.value()); // RefreshToken의 value() 메서드로 토큰 문자열 추출
    }

    /**
     * JWT 토큰 문자열을 파싱하여 클레임(Payload)을 추출합니다.
     * 이 과정에서 토큰의 서명 유효성 검증, 만료 여부 등 다양한 유효성 검사를 수행합니다.
     * JWT 0.12.x 버전의 최신 파싱 문법을 사용합니다.
     *
     * @param token 파싱할 JWT 문자열
     * @return 파싱된 JWT 클레임(Claims) 객체
     * @throws BussinessException 토큰이 유효하지 않거나 만료되었을 때 등 다양한 JWT 관련 예외 발생 시
     */
    public Claims parse(String token) {
        try {
            // Jwts.parser()로 파서 빌더를 시작하고, .verifyWith(key)로 서명 검증에 사용할 비밀 키를 설정합니다.
            // .build()로 파서를 생성한 후, .parseSignedClaims(token)으로 서명된 JWT를 파싱합니다.
            // .getPayload()로 JWT의 페이로드(클레임 정보)를 가져옵니다.
            return Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token) // JWS(JSON Web Signature)를 파싱하여 서명 검증
                    .getPayload(); // 페이로드(클레임) 추출
        } catch (IllegalArgumentException e) {
            // 토큰이 null이거나 비어있을 때 발생하는 예외
            throw new BussinessException(ErrorCode.ILLEGAL_TOKEN, "토큰이 유효하지 않습니다: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            // 토큰의 유효 기간이 만료되었을 때 발생하는 예외
            throw new BussinessException(ErrorCode.EXPIRED_TOKEN, "토큰이 만료되었습니다: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 형식일 때 발생하는 예외 (예: JWE가 아닌 JWT)
            throw new BussinessException(ErrorCode.UNSUPPORTED_TOKEN, "지원되지 않는 토큰입니다: " + e.getMessage());
        } catch (MalformedJwtException e) {
            // 토큰의 형식이 올바르지 않을 때 발생하는 예외 (예: Base64 디코딩 실패, JSON 파싱 실패)
            throw new BussinessException(ErrorCode.MALFORMED_TOKEN, "잘못된 형식의 토큰입니다: " + e.getMessage());
        } catch (JwtException e) {
            // 위에 명시된 예외 외의 JWT 관련 일반적인 예외
            throw new BussinessException(ErrorCode.UNKNOWN_TOKEN, "알 수 없는 토큰 오류: " + e.getMessage());
        } catch (Exception e) {
            // 예상치 못한 모든 종류의 예외 (매우 중요, 놓칠 수 있는 모든 오류를 포착)
            throw new BussinessException(ErrorCode.INTERNAL_SERVER_ERROR, "JWT 파싱 중 서버 오류: " + e.getMessage());
        }
    }

    /**
     * Access Token의 설정된 만료 기간을 반환합니다.
     * @return Access Token 만료 기간 (밀리초)
     */
    public Long getAccessTokenExpirePeriod() {
    	return accessTokenExpirePeriod;
    }

    /**
     * Refresh Token의 설정된 만료 기간을 반환합니다.
     * @return Refresh Token 만료 기간 (밀리초)
     */
    public Long getRefreshTokenExpirePeriod() {
    	return refreshTokenExpirePeriod;
    }

    /**
     * 주어진 클레임 맵과 만료 시간을 사용하여 JWT 토큰을 생성합니다.
     * JWT 0.12.x 버전의 최신 토큰 빌더 문법을 사용합니다.
     * HS512 알고리즘으로 토큰에 서명합니다.
     *
     * @param claims 토큰에 포함될 정보(클레임)의 Map (예: 사용자 이메일, 역할 등)
     * @param expireTime 토큰의 유효 기간 (밀리초 단위)
     * @return 생성된 JWT 문자열
     */
    public String generateToken(Map<String, Object> claims, Long expireTime) {
    	long now = System.currentTimeMillis(); // 현재 시간 (밀리초)

    	return Jwts.builder()
    			.claims(claims) // JWT 페이로드에 클레임 맵을 설정합니다. (JJWT 0.12.x 문법)
    			.issuedAt(new Date(now)) // 토큰 발행 시간(iat)을 현재 시간으로 설정합니다.
    			.expiration(new Date(now + expireTime)) // 토큰 만료 시간(exp)을 현재 시간 + 유효 기간으로 설정합니다.
    			.signWith(key, Jwts.SIG.HS512) // HMAC-SHA512 알고리즘과 비밀 키로 토큰에 서명합니다. (JJWT 0.12.x 문법)
    			.compact(); // 모든 설정을 바탕으로 JWT 문자열을 압축하여 생성합니다.
    }

}
