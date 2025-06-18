package com.ssafy.notification.repository;

import com.ssafy.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	// Notification 엔티티를 기반으로 Long 타입의 기본 키 사용.
	// CRUD 메서드인 save, findById, delete 등을 자동 제공
    List<Notification> findByUserId(Long userId);
}
