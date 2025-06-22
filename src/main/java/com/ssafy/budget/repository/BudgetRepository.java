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
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BudgetRepository { 
	
	@Select("""
			SELECT id,user_id,category_id,amount,start_date,end_date 
			from budgets WHERE user_id = #{userId}
			""")
	List<Budget> findByUserId(Long userId);
	
	
	//budget와 user가 맞는 지 검증
	@Select("""
            SELECT COUNT(*) > 0
            FROM budgets
            WHERE id = #{budgetId} AND user_id = #{userId}
            """)
    boolean existsByIdAndUserId(@Param("budgetId") Long budgetId, @Param("userId") Long userId);

	
	//budget 생성
	@Insert("INSERT INTO budgets (id,user_id,category_id,amount,start_date,end_date) VALUES (#{id},#{user_id},#{category_id},#{amount},#{start_date},#{end_date})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Budget budge);
	
	
	//budget 삭제
    @Delete("DELETE FROM budgets WHERE user_id = #{userId} AND id = #{budgetId}")
    void delete(@Param("userId") Long userId, @Param("postId") Long budgetId);
    
    
    //budget 수정
    @Update("""
            UPDATE budgets
            SET
                category_id = #{budget.categoryId},
                amount = #{budget.amount},
                start_date = #{budget.startDate},
                end_date = #{budget.endDate}
            WHERE id = #{budgetId} AND user_id = #{userId}
            
            """)
    void update(@Param("budget") Budget budget, @Param("budgetId") Long budgetId, @Param("userId") Long userId);

}
