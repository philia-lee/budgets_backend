package com.ssafy.budget.repository;

import com.ssafy.budget.entity.Budget;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BudgetRepository {

    @Select("""
        SELECT id,user_id,category_id,amount,start_date,end_date
        from budgets WHERE user_id = #{userId}
    """)
    List<Budget> findByUserId(Long userId);

    @Select("""
        SELECT COUNT(*) > 0
        FROM budgets
        WHERE id = #{budgetId} AND user_id = #{userId}
    """)
    boolean existsByIdAndUserId(@Param("userId") Long userId, @Param("budgetId") Long budgetId);

    //budget 생성
    @Insert("""
        INSERT INTO budgets
        (user_id,category_id,amount,start_date,end_date)
        VALUES
        (#{budget.user_id},#{budget.category_id},#{budget.amount},#{budget.start_date},#{budget.end_date})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(@Param("budget") Budget budget); // [수정] budge → budget

    //budget 삭제
    @Delete("DELETE FROM budgets WHERE user_id = #{userId} AND id = #{budgetId}")
    void delete(@Param("userId") Long userId, @Param("budgetId") Long budgetId); // [수정] postId → budgetId

    //budget 수정
    @Update("""
        UPDATE budgets
        SET
            category_id = #{budget.category_id},
            amount = #{budget.amount},
            start_date = #{budget.start_date},
            end_date = #{budget.end_date}
        WHERE id = #{budgetId} AND user_id = #{userId}
    """)
    void update(@Param("budget") Budget budget, @Param("budgetId") Long budgetId, @Param("userId") Long userId);
}
