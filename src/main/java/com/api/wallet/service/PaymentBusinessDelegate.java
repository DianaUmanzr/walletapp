package com.api.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.wallet.dto.request.PaymentOnTopRequestDto;
import com.api.wallet.dto.request.PaymentRequestDto;
import com.api.wallet.dto.response.PaymentResponseDto;
import com.api.wallet.entity.Audit;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.Transaction;
import com.api.wallet.entity.enums.TransactionStatusType;

@Component
public class PaymentBusinessDelegate {
	
	@Autowired
	private PaymentService paymentService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Transactional
	public PaymentResponseDto createPaymentTransaction(PaymentOnTopRequestDto paymentRequestDto) {
		Page<Transaction> transactions = transactionService.getLastBalanacedByAccountNumber(paymentRequestDto.getSource().getAccount().getAccountNumber());
		List<Transaction> transactionsList = transactions.getContent();
		
		Optional<BankAccount> bankAccountOptional =  bankAccountService.findOne(new Specification<BankAccount>() {
			
			@Override
			public Predicate toPredicate(Root<BankAccount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("accountNumber"), paymentRequestDto.getSource().getAccount().getAccountNumber());
			}
		});
		
		PaymentRequestDto request = new PaymentRequestDto();
		request.setAmount(paymentRequestDto.getAmount());
		request.setDestination(paymentRequestDto.getDestination());
		request.setSource(paymentRequestDto.getSource());
		
		PaymentResponseDto response = paymentService.createPaymentTransaction(request);
		
		if(!Objects.isNull(transactions) && !transactions.isEmpty()) {
			Optional<Transaction> optional = transactionsList.stream().findFirst();
			if(optional.isPresent()) {
				Transaction transaction = optional.get();
				
				Transaction transactionToPersist = Transaction
						.builder()
						.amount(paymentRequestDto.getAmount())
						.auditComposition(Audit
								.AuditBuilder
								.anAudit()
								.forCreation(paymentRequestDto.getUserId().toString(),LocalDateTime.now())
								.build())
						.balance(transaction.getBalance().subtract(paymentRequestDto.getAmount()))
						.bankAccount(bankAccountOptional.get())
						.fee(paymentRequestDto.getAmount().multiply(new BigDecimal("0.10")))
						.status(response.getRequestInfo().getStatus())
						.transactionDestinationId(response.getPaymentInfo().getId().toString())
						.type(TransactionStatusType.DEBIT)
						.build();
				
				
				transactionService.save(transactionToPersist);
				
			}
			
		}else {
			
			Optional<Transaction> optional = transactionsList.stream().findFirst();
			Transaction transaction = optional.get();
			
			Transaction transactionToPersist = Transaction
					.builder()
					.amount(paymentRequestDto.getAmount())
					.auditComposition(Audit
							.AuditBuilder
							.anAudit()
							.forCreation(paymentRequestDto.getUserId().toString(),LocalDateTime.now())
							.build())
					.balance(transaction.getBalance().subtract(paymentRequestDto.getAmount()))
					.bankAccount(bankAccountOptional.get())
					.fee(paymentRequestDto.getAmount().multiply(new BigDecimal("0.10")))
					.status(response.getRequestInfo().getStatus())
					.transactionDestinationId(response.getPaymentInfo().getId().toString())
					.type(TransactionStatusType.DEBIT)
					.build();
			
				transactionService.save(transactionToPersist);
		}
		
		
		return response;
	}

}
