package com.api.wallet.service;

import com.api.wallet.dto.response.TransactionDTO;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.User;
import java.util.List;

import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.api.wallet.entity.Transaction;
import com.api.wallet.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;

	private final ModelMapper modelMapper;

	public TransactionService(TransactionRepository transactionRepository, ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.transactionRepository = transactionRepository;
	}


	public Page<Transaction> getLastBalanacedByAccountNumber(String accountNumber) {
		return transactionRepository.findAll(new Specification<Transaction>() {
			
			@Override
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				return criteriaBuilder.equal(root.get("bankAccount").<String>get("accountNumber"), accountNumber);
			}
		}, PageRequest.of(0, 1, Direction.DESC, "auditComposition.created"));	
	}
	
	public <S extends Transaction> S save(S entity) {
		return transactionRepository.save(entity);
	}

	public Page<Transaction> getTransactionsByUserIdOrderedDesc(String userId, Pageable pageable) {
		return transactionRepository.findAll(new Specification<Transaction>() {
			public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Join<Transaction, BankAccount> joinTB = root.join("bankAccount");
				Join<BankAccount, User> joinBU = joinTB.join("user");
				return criteriaBuilder.equal(joinBU.get("userId"), userId);
			}
		}, pageable);
	}

	public Page<TransactionDTO> convertTransactionEntityToDTO(String userId, Pageable pageable) {
		Page<Transaction> transactions = getTransactionsByUserIdOrderedDesc(userId, pageable);
		List<TransactionDTO> transactionDTOs = transactions.getContent().stream()
				.map(transaction -> {
					transactions.get().forEach(au ->{
						transaction.setAuditComposition(au.getAuditComposition());
					});
					return modelMapper.map(transaction, TransactionDTO.class);
				})
				.collect(Collectors.toList());

		return new PageImpl<>(transactionDTOs, transactions.getPageable(), transactions.getTotalElements());
	}
}
