// 예산 데이터를 나타내는 엔티티
package com.ssafy.budget.entity;

import jakarta.persistence.*; // Java Persistence API(JPA)의 핵심 이노테이션(@entity, @Id, @GeneratedValue, @Column)등을 한 번에 가져오는 import문
// jakarta.persistence란 JPA는 자바 객체와 DB 테이블 간 매핑을 위한 표준 인터페이스
import lombok.*;
import java.time.LocalDate;

@Entity // 이 클래스가 JPA 엔티티임을 선언 -> budget 테이블에 대응됨.
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id // 이 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동 생성되는 AUTO_INCREMENT 방식 적용
    private Long id;

    private Long userId;              // 사용자 ID
    private String category;          // 카테고리 (예: 식비, 교통비)
    private Integer amount;           // 설정 예산
    private LocalDate startDate;      // 시작일
    private LocalDate endDate;        // 종료일
}
