package com.ssafy.auth.jwt.provider;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ssafy.auth.jwt.Token.AccessToken;
import com.ssafy.auth.jwt.Token.RefreshToken;
import com.ssafy.auth.jwt.util.JwtUtil;
import com.ssafy.user.entity.User;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtUtil jwtUtil;
    
    public AccessToken issueAccessToken(User user) {
        Map<String, Object> claims = Map.of(
        		JwtUtil.ID, user.getId(),
                JwtUtil.TYPE, JwtUtil.ACCESS,
                JwtUtil.NAME, user.getNickname()
        );
        String token = jwtUtil.generateToken(claims, jwtUtil.getAccessTokenExpirePeriod());
        return new AccessToken(token);
    }
    public RefreshToken issueRefreshToken(User user) {
        Map<String, Object> claims = Map.of(
                JwtUtil.TYPE, JwtUtil.REFRESH,
                JwtUtil.ID, user.getId()
        );
        String token = jwtUtil.generateToken(claims, jwtUtil.getRefreshTokenExpirePeriod());
        return new RefreshToken(token);
    }
    


    
    

}
