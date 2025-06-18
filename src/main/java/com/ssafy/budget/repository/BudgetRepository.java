// DB에서 userId로 예산 데이터를 조회하는 인터페이스
package com.ssafy.budget.repository; 

import com.ssafy.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> { 
	// BudgetRepository는 Budget 엔티티에 대한 DB 접근 기능을 자동으로 구현해주는 JPA 인터페이스
	// JpaRepository<엔티티클래스, ID타입>
    List<Budget> findByUserId(Long userId);
}
