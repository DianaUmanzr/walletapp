package com.api.wallet.controller;

import com.api.wallet.dto.response.TransactionDTO;
import com.api.wallet.entity.Transaction;
import com.api.wallet.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

   @GetMapping
    public ResponseEntity<Page<TransactionDTO>> getLastBalanceByAccountNumber(@RequestParam String userId,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size, Direction.DESC, "auditComposition.created");
        Page<TransactionDTO> transactions = transactionService.convertTransactionEntityToDTO(userId, pageable);
        return ResponseEntity.ok().body(transactions);
    }
}
