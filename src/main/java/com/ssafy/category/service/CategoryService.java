package com.ssafy.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.category.dto.CategoryResponse;
import com.ssafy.category.dto.CreateCategory;
import com.ssafy.category.entity.Category;
import com.ssafy.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository repository;
	
	@Transactional
	public void create(Long userId, CreateCategory request)
	{
		Category category = Category.builder()
							.name(request.getName())
							.type(request.getType())
							.icon(request.getIcon())
							.color(request.getColor())
							.build();
		repository.save(userId, category); 
							
	}
	
	@Transactional(readOnly = true) 
	public List<CategoryResponse>  allshow(Long userId)
	{
		List<CategoryResponse> category = repository.findAllCategory(userId);
		return category;
	}
}
