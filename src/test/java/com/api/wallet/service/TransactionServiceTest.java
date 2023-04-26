package com.api.wallet.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.api.wallet.dto.response.TransactionDTO;
import com.api.wallet.entity.Audit;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.Transaction;
import com.api.wallet.entity.User;
import com.api.wallet.repository.TransactionRepository;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TransactionService transactionService;

    private BankAccount bankAccount;
    private Transaction transaction;
    private Audit audit;
    private User user;

    @BeforeEach
    void setUp() {

        transactionService = new TransactionService(transactionRepository, modelMapper);

        user = User.builder().build();
        user.setUserId(1234L);

        bankAccount = BankAccount.builder().build();
        bankAccount.setAccountNumber("123456789");
        bankAccount.setUser(user);

        audit = new Audit();
        audit.setCreated(LocalDateTime.now());

        transaction = Transaction.builder().build();
        transaction.setTransactionId(1L);
        transaction.setAmount(new BigDecimal(100));
        transaction.setBankAccount(bankAccount);
        transaction.setAuditComposition(audit);
        transaction.setBankAccount(bankAccount);
    }

    @Test
    @DisplayName("Test getTransactionsByUserIdOrderedDesc method")
    void testGetTransactionsByUserIdOrderedDesc() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Pageable pageable = PageRequest.of(0, 1, Direction.DESC, "auditComposition.created");

        when(transactionRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable)))
                .thenReturn(new PageImpl<>(transactions));

        Page<Transaction> result = transactionService.getTransactionsByUserIdOrderedDesc("1234", pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getContent().get(0)).isEqualTo(transaction);
    }

    @Test
    @DisplayName("Test get transactions by user id ordered descending")
    void testGetTransactionsByUserIdOrderedDesc2() {
        // given
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId(1L);
        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId(2L);
        transactions.add(transaction1);
        transactions.add(transaction2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Transaction> expectedTransactions = new PageImpl<>(transactions, pageRequest, transactions.size());

        when(transactionRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(expectedTransactions);

        // when
        Page<Transaction> actualTransactions = transactionService.getTransactionsByUserIdOrderedDesc("123456789", pageRequest);

        // then
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    @DisplayName("Test get last balanced transaction by account number")
    void testGetLastBalanacedByAccountNumber3() {
        // given
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId(1L);
        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId(2L);
        transactions.add(transaction1);
        transactions.add(transaction2);

        PageRequest pageRequest = PageRequest.of(0, 1, Sort.Direction.DESC, "auditComposition.created");

        Page<Transaction> expectedTransactions = new PageImpl<>(transactions, pageRequest, transactions.size());

        when(transactionRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(expectedTransactions);

        // when
        Page<Transaction> actualTransactions = transactionService.getLastBalanacedByAccountNumber("123456789");

        // then
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testConvertTransactionEntityToDTO() {
        String userId = "user123";
        Pageable pageable = Pageable.unpaged();

        List<Transaction> transactionsList = Arrays.asList(transaction);
        Page<Transaction> page = new PageImpl<>(transactionsList);

        when(transactionRepository.findAll(
                ArgumentMatchers.any(Specification.class),
                ArgumentMatchers.any(Pageable.class)
        )).thenReturn(page);

        Page<TransactionDTO> transactionDTOPage = transactionService.convertTransactionEntityToDTO(userId, pageable);
        assertEquals(1, transactionDTOPage.getContent().size());
    }
}
