package com.ssafy.transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.auth.exception.ErrorCode;
import com.ssafy.auth.exception.exception.BusinessException;
import com.ssafy.transaction.dto.CreateTransactionRequest;
import com.ssafy.transaction.dto.UpdateTransaction;
import com.ssafy.transaction.entity.Transaction;
import com.ssafy.transaction.repository.TransactionRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final TransactionRepository transactionRepository;
	
	@Transactional
	public void create(Long userId,CreateTransactionRequest request)
	{
		Transaction transaction = Transaction.builder()
								  .amount(request.getAmount())
								  .type(request.getType())
								  .description(request.getDescription())
								  .category_id(request.getCategory_id())
								  .date(request.getDate())
								  .build();
		transactionRepository.save(userId,transaction);
	}
	
	@Transactional
	public void Update(Long userId,Long id,UpdateTransaction request)
	{
		Boolean existingBudget = transactionRepository.existsByIdAndUserId(userId, id);
    	if (!existingBudget) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

		Transaction transaction = Transaction.builder()
								  .amount(request.getAmount())
								  .type(request.getType())
								  .description(request.getDescription())
								  .date(request.getDate())
								  .build();
    	transactionRepository.update(userId,id, transaction);
	}
	
	@Transactional
	public void Delete(Long userId,Long id)
	{
		Boolean existingBudget = transactionRepository.existsByIdAndUserId(userId, id);
    	if (!existingBudget) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }
    	transactionRepository.delete(userId, id);
    	
	}
}
