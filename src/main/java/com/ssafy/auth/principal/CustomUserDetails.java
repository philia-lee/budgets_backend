package com.ssafy.auth.principal; // 패키지 경로는 기존과 동일하게 유지

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List; // List.of()를 사용하려면 필요
import java.util.Collections; // Collections.singletonList()를 사용하려면 필요

@ToString
@RequiredArgsConstructor // final 필드를 위한 생성자를 자동으로 생성해 줍니다.
public class CustomUserDetails implements UserDetails {

    @Getter private final Long id;   // 사용자 ID (JWT 클레임에서 Integer로 추출된다고 가정)
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 'role' 필드가 없거나 사용하지 않는 경우, 모든 인증된 사용자에게 기본 권한 부여
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return id.toString();
	}

}