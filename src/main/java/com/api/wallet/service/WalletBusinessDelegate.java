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

import com.api.wallet.dto.request.WalletInformationRequestDTO;
import com.api.wallet.dto.request.WalletTransactionRequestDto;
import com.api.wallet.dto.response.WalletTransactionResponseDto;
import com.api.wallet.entity.Audit;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.Transaction;
import com.api.wallet.entity.enums.TransactionStatusType;

@Component
public class WalletBusinessDelegate {
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private WalletService walletService;
	
	
	@Transactional
    public WalletTransactionResponseDto createWalletTransaction(WalletInformationRequestDTO walletInformationRequestDTO) {
		WalletTransactionRequestDto walletTransactionRequestDto = new WalletTransactionRequestDto();
		walletTransactionRequestDto.setAmount(walletInformationRequestDTO.getAmount());
		walletTransactionRequestDto.setUserId(walletInformationRequestDTO.getUserId());
		
		Page<Transaction> transactions = transactionService.getLastBalanacedByAccountNumber(walletInformationRequestDTO.getAccountNumber());
		List<Transaction> transactionsList = transactions.getContent();
		
		WalletTransactionResponseDto response = walletService.createWalletTransaction(walletTransactionRequestDto);
		
		Optional<BankAccount> bankAccountOptional =  bankAccountService.findOne(new Specification<BankAccount>() {
			
			@Override
			public Predicate toPredicate(Root<BankAccount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("accountNumber"), walletInformationRequestDTO.getAccountNumber());
			}
		});
		
		if(!Objects.isNull(transactions) && !transactions.isEmpty()) {
			Optional<Transaction> optional = transactionsList.stream().findFirst();
			if(optional.isPresent()) {
				Transaction transaction = optional.get();
				
				Transaction transactionToPersist = Transaction
						.builder()
						.amount(walletTransactionRequestDto.getAmount())
						.auditComposition(Audit
								.AuditBuilder
								.anAudit()
								.forCreation(response.getUserId().toString(),LocalDateTime.now())
								.build())
						.balance(transaction.getBalance().add(walletTransactionRequestDto.getAmount()))
						.bankAccount(bankAccountOptional.get())
						.fee(new BigDecimal("0.00"))
						.status("WALLET_DEBIT_OK")
						.transactionDestinationId(response.getWalletTransactionId().toString())
						.type(TransactionStatusType.CREDIT)
						.build();
				
				transactionService.save(transactionToPersist);
			}
		}else {
			Transaction transactionToPersist = Transaction
					.builder()
					.amount(walletTransactionRequestDto.getAmount())
					.auditComposition(Audit
							.AuditBuilder
							.anAudit()
							.forCreation(response.getUserId().toString(),LocalDateTime.now())
							.build())
					.balance(walletTransactionRequestDto.getAmount())
					.bankAccount(bankAccountOptional.get())
					.fee(new BigDecimal("0.00"))
					.status("WALLET_DEBIT_OK")
					.transactionDestinationId(response.getWalletTransactionId().toString())
					.type(TransactionStatusType.CREDIT)
					.build();
			
			transactionService.save(transactionToPersist);
		}

		return response;
    	
    }

}
