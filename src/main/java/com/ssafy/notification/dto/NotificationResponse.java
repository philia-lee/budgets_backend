package com.ssafy.notification.dto;

import lombok.*;
import java.time.LocalDateTime;

// DTO 클래스이며 Entity와는 다르게 사용자에게 보여주기 위한 형식(Entity는 DB 전용)
@Getter @Setter @Builder
public class NotificationResponse {
    private Long id;
    private String message;
    private String type;
    private boolean isRead;
    private LocalDateTime createdAt;
}
