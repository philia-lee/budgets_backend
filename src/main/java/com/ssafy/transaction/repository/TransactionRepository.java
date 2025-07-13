package com.ssafy.transaction.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ssafy.budget.entity.Budget;
import com.ssafy.transaction.dto.CreateTransactionRequest;
import com.ssafy.transaction.dto.TransactionResponse;
import com.ssafy.transaction.dto.UpdateTransaction;
import com.ssafy.transaction.entity.Transaction;

@Mapper
public interface TransactionRepository {

	@Insert("""
	        INSERT INTO transactions
	        (user_id, type, amount, category_id, description, date, created_at)
	        VALUES
	        (
	            #{userId},
	            #{transaction.type},
	            #{transaction.amount},
	            (
	                SELECT id
	                FROM categories
	                WHERE name = #{transaction.category} AND (user_id = #{userId})
	            ),
	            #{transaction.description},
	            #{transaction.date},
	            NOW()
	        )
	        """)
	void save(@Param("userId") Long userId,
			@Param("transaction") CreateTransactionRequest transaction);
	
	@Select("SELECT count(*) FROM transactions WHERE id = #{id} and user_id=#{userId}")
	Boolean existsByIdAndUserId(@Param("userId") Long userId,@Param("id") Long id);
	
	@Update("""
	         UPDATE transactions
       SET
           category_id = (
               SELECT id
               FROM categories
               WHERE name = #{transaction.category} AND (user_id = #{userId})
           ),
           type = #{transaction.type},
           amount = #{transaction.amount},
           description = #{transaction.description},
           date = #{transaction.date}
       WHERE id = #{id} AND user_id = #{userId}
	        """)
	void update(@Param("userId") Long userId, @Param("id") Long id, @Param("transaction") UpdateTransaction transaction);
	
	@Select("""
			SELECT
		    t.id,
		    t.user_id,
		    t.type,
		    t.amount,
		    c.name AS category,
		    t.description,
		    t.date
		FROM
		    transactions t
		JOIN
		    categories c ON t.category_id = c.id
		WHERE
		    t.user_id = #{userId} && t.id = #{id};
		""")
	TransactionResponse show(@Param("userId") Long userId,@Param("id") int id);
	
	@Delete("DELETE FROM transactions  WHERE id = #{id} AND user_id = #{userId}")
	void delete(@Param("userId") Long userId, @Param("id") Long id);

	@Select("""
			SELECT
			    t.id,
			    t.user_id,
			    t.type,
			    t.amount,
			    c.name AS category,
			    t.description,
			    t.date
			FROM
			    transactions t
			JOIN
			    categories c ON t.category_id = c.id
			WHERE
			    t.user_id = #{userId};
			""")
	List<TransactionResponse> allshow(@Param("userId") Long userId);
}