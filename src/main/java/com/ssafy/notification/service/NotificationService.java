package com.ssafy.notification.service;

import com.ssafy.notification.dto.NotificationResponse;
import com.ssafy.notification.entity.Notification;
import com.ssafy.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public List<NotificationResponse> getUserNotifications(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(n -> NotificationResponse.builder()
                        .id(n.getId())
                        .message(n.getMessage())
                        .type(n.getType())
                        .isRead(n.isRead())
                        .createdAt(n.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public void updateNotificationStatus(Long userId, Long notificationId, boolean isRead) {
        repository.updateIsRead(userId, notificationId, isRead);
    }
}
