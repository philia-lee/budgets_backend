package com.ssafy.budget.dto;

import lombok.*; // lombok 어노테이션
import java.time.LocalDate; // 날짜 타입

import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter // 모든 필드에 대한 getter/setter 자동 생성
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 생성
@Builder // 빌더 패턴을 사용해 객체를 만들 수 있게 함
@Schema(description = "새로운 가계부 항목을 생성하기 위한 요청 데이터")
public class CreateBudgetRequest { // 클라이언트가 예산 생성 요청을 보낼 때 사용하는 DTO 클래스
	@Schema(description = "가계부 항목의 카테고리 ID. 미리 정의된 유효한 카테고리 ID(외래키)여야 합니다",
			example = "1",
			requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer category_id;
    private Integer amount;
    private LocalDate startDate;
    private LocalDate endDate;
}
