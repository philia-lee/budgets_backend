package com.ssafy.notification.repository;

import com.ssafy.notification.entity.Notification;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NotificationRepository {

    @Select("SELECT id, user_id, message, type, is_read, created_at FROM notifications WHERE user_id = #{userId}")
    List<Notification> findByUserId(Long userId);

    @Insert("INSERT INTO notifications (user_id, message, type, is_read, created_at) VALUES (#{userId}, #{message}, #{type}, #{isRead}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Notification alarm);

    @Update("UPDATE notifications SET is_read = #{isRead} WHERE user_id = #{userId} AND id = #{notificationId}")
    void updateIsRead(@Param("userId") Long userId, @Param("notificationId") Long notificationId, @Param("isRead") boolean isRead);
}
