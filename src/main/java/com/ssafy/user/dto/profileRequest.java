package com.ssafy.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class profileRequest {
	String nickname;
	Date birthdate;
	String gender;
}
