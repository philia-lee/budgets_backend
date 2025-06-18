package com.ssafy.notification.service; // 알림 도메인의 서비스 계층을 의미

import com.ssafy.notification.dto.NotificationResponse;
import com.ssafy.notification.entity.Notification;
import com.ssafy.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor; // repository를 생성자 없이 자동으로 주입 가능하게 만들어 줌.
import org.springframework.stereotype.Service; // spring service 빈으로 등록되도록 하는 어노테이션.

import java.util.List; // 알림 목록을 반환할 때 사용
import java.util.stream.Collectors; // 스트림에서 .collect()를 사용하개 해줌

@Service // 서비스 계층 -> 비즈니스 로직 담당. Bean 등록. 
@RequiredArgsConstructor // NotificationRepository repository가 자동으로. 즉, 생성자가 자동으로 주입.
public class NotificationService {

    private final NotificationRepository repository;

    public List<NotificationResponse> getUserNotifications(Long userId) {
        return repository.findByUserId(userId).stream() // 특정 사용자에 대한 알림 목록 반환 메서드
                .map(n -> NotificationResponse.builder()
                        .id(n.getId())
                        .message(n.getMessage())
                        .type(n.getType())
                        .isRead(n.isRead())
                        .createdAt(n.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
