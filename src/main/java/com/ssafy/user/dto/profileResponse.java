package com.ssafy.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class profileResponse {
	String email;
	String nickname;
	Date birthdate;
	String gender;
	Date created_at;
}
