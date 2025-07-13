package com.ssafy.statistic.repository;

import com.ssafy.transaction.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.Date;
import java.util.List;

@Mapper
public interface StatisticRepository {

    // 1. 특정 기간, 사용자, (선택)카테고리의 지출(Expense) 거래 조회
    @Select({
        "<script>",
        "SELECT * FROM transactions",
        "WHERE user_id = #{user_id}",
        "AND type = 'EXPENSE'",
        "<if test='category_ids != null'>",
        "AND category_id IN",
        "<foreach collection='category_ids' item='id' open='(' separator=',' close=')'>",
        "#{id}",
        "</foreach>",
        "</if>",
        "AND date BETWEEN #{start_date} AND #{end_date}",
        "</script>"
    })
    List<Transaction> findExpensesByUserAndCategoriesAndDate(
        @Param("user_id") Long user_id,
        @Param("category_ids") List<Integer> category_ids,
        @Param("start_date") Date start_date,
        @Param("end_date") Date end_date
    );

    // 2. 특정 기간, 사용자, (선택)카테고리의 전체 거래(INCOME/EXPENSE) 조회
    @Select({
        "<script>",
        "SELECT * FROM transactions",
        "WHERE user_id = #{user_id}",
        "<if test='category_ids != null'>",
        "AND category_id IN",
        "<foreach collection='category_ids' item='id' open='(' separator=',' close=')'>",
        "#{id}",
        "</foreach>",
        "</if>",
        "AND date BETWEEN #{start_date} AND #{end_date}",
        "</script>"
    })
    List<Transaction> findTransactionsByUserAndCategoriesAndDate(
        @Param("user_id") Long user_id,
        @Param("category_ids") List<Integer> category_ids,
        @Param("start_date") Date start_date,
        @Param("end_date") Date end_date
    );
}
