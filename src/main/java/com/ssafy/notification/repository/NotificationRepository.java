package com.ssafy.notification.repository;

import com.ssafy.notification.entity.Notification;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotificationRepository {
	// Notification 엔티티를 기반으로 Long 타입의 기본 키 사용.
	
	@Select("SELECT id,user_id,meesage,type,is_read,created_at WHERE user_id = #{userId}")
    List<Notification> findByUserId(Long userId);
    
	@Insert("INSERT into notifications (id,user_id as userId,message,type,is_read,create_at) VALUES (#{id},#{userId} ,#{message},#{type},#{is_read},#{create_at})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Notification alarm);
}
