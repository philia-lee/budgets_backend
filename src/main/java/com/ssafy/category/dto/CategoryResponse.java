package com.ssafy.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter; // 필요에 따라 추가. 보통 응답 DTO는 Getter만 있어도 됨.

@Getter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class CategoryResponse {
    private Long id;
    private String name;
    private String type;
    private String icon;
    private String color;
}