package com.ssafy.notification.dto;

import lombok.*;

@Getter
@Setter
public class UpdateNotificationStatusRequest {
    private boolean isRead;
}
// 알림 읽음 상태 업데이트에 사용되는 요청용 DTO.
// ex) 사용자가 알림을 클릭하면 isRead: true로 변경 요청을 보낼 때 사용됨