package com.ssafy.notification.controller;

import com.ssafy.notification.dto.NotificationResponse;
import com.ssafy.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // rest API 응답을 반환하는 컨트롤러를 의미
@RequestMapping("/api/notifications") // 기본 URI가 /api/notifications로 시작
@RequiredArgsConstructor // notificationService를 생성자 주입 방식으로 자동 주입
public class NotificationController {

    private final NotificationService notificationService; // 알림 비즈니스 로직을 담당하는 서비스 계층 객체.

    @GetMapping("/{userId}") // HTTP GET 요청, URI에 userId가 포함됨. ex) /api/notifications/3
    public List<NotificationResponse> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId); // service 계층에 위임 -> DTO로 가공된 알림 목록을 받아 클라이언트에게 JSON으로 반환
    }
}
