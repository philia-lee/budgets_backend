package com.ssafy.notification.controller;

import com.ssafy.notification.dto.NotificationResponse;
import com.ssafy.notification.dto.UpdateNotificationStatusRequest;
import com.ssafy.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<NotificationResponse> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @PatchMapping("/{userId}")
    public void updateNotificationStatus(@PathVariable Long userId,
                                         @RequestBody UpdateNotificationStatusRequest req) {
        notificationService.updateNotificationStatus(userId, req.getId(), req.isRead());
    }
}
