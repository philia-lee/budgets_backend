package com.ssafy.notification.entity;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private Long id;
    private Long userId;
    private String message;
    private String type;
    private boolean isRead;
    private LocalDateTime createdAt;
}
