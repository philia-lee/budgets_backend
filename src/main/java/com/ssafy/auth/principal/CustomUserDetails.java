// src/main/java/com/ssafy/auth/principal/CustomUserDetails.java
package com.ssafy.auth.principal; // 이 패키지 경로를 사용합니다.

import com.ssafy.user.entity.User; // 유저 엔티티 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String email;
    private String password;
    // private String role; // User 엔티티에 역할 필드가 있다면 추가

    public CustomUserDetails(User user) {
        this.id = user.getId(); // long을 Integer로 변환
        this.email = user.getEmail();
        this.password = user.getPassword();
        // this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        // return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.toUpperCase())); // role 필드 사용 시
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return this.id;
    }
}