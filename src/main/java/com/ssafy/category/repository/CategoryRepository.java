package com.ssafy.category.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ssafy.category.dto.CategoryResponse;
import com.ssafy.category.entity.Category;
import com.ssafy.common.annotation.UserId; 

@Mapper
public interface CategoryRepository {

	@Insert("""
			INSERT INTO categories
			(user_id, name, type, icon, color)
			VALUES
			(#{user}, #{category.name}, #{category.type}, #{category.icon}, #{category.color})
			""")
	void save(@Param("user") Long userId, @Param("category") Category category);

	@Select("SELECT id, name, type, icon, color FROM categories WHERE user_id = #{userId}")
	List<CategoryResponse> findAllCategory(@Param("userId") Long userId); // @Param을 명시적으로 붙여주는 것이 좋습니다.

}