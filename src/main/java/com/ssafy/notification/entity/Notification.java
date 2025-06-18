package com.ssafy.notification.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // DB 테이블과 매핑됨
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // 어떤 유저에게 보낸 알림인지. 나머지는 dto의 response와 구조가 같음. 이 entity는 DB 전용

    private String message;

    private String type; // 예: BUDGET_OVER, GOAL_REACHED 등

    private boolean isRead;

    private LocalDateTime createdAt;
}
