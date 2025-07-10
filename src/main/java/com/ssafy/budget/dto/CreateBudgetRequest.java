package com.ssafy.budget.dto;

import lombok.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "새로운 가계부 항목을 생성하기 위한 요청 데이터")
public class CreateBudgetRequest {
    @Schema(description = "가계부 항목의 카테고리 ID. 미리 정의된 유효한 카테고리 ID(외래키)여야 합니다",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer category_id;

    private Integer amount;

    private LocalDate startDate; // [수정] 프론트엔드와 맞추기 위해 camelCase 유지

    private LocalDate endDate;   // [수정] 프론트엔드와 맞추기 위해 camelCase 유지
}
