package com.ssafy.notification.dto;

import lombok.*;

@Getter
@Setter
public class UpdateNotificationStatusRequest {
    private Long id;       // ← 반드시 추가!
    private boolean isRead;
    // 알림 읽음 상태 업데이트에 사용되는 요청용 DTO.
}
