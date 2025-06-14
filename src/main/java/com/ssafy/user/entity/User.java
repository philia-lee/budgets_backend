package com.ssafy.user.entity;
// 프로필 정보
import java.sql.Date;
import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor //기본 생성자
public class User {
	private long id;
	private String email;
	private String password;
	private String nickname;
	private Date createdAt;
	

	
}
