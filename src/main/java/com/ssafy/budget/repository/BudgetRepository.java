// DB에서 userId로 예산 데이터를 조회하는 인터페이스
package com.ssafy.budget.repository; 

import com.ssafy.budget.entity.Budget;
import com.ssafy.user.entity.User;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BudgetRepository { 
	
	@Select("SELECT id,user_id,category_id,amount,start_date,end_date from budgets WHERE user_id = #{userId}")
	List<Budget> findByUserId(Long userId);
	
	@Insert("INSERT INTO budgets (id,user_id,category_id,amount,start_date,end_date) VALUES (#{id},#{user_id},#{category_id},#{amount},#{start_date},#{end_date})")
	@Options(useGeneratedKeys = true, keyProperty = "id") // DB가 생성한 ID를 User 객체의 id 필드에 주입
    void save(Budget budge);
	
	
    @Delete("DELETE FROM budgets WHERE user_id = #{userId} AND id = #{postId}")
    void delete(@Param("userId") Long userId, @Param("postId") Long postId);
}
