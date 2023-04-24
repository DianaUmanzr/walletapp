package com.api.wallet.dto.response;

import com.api.wallet.entity.enums.TransactionStatusType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class TransactionDTO implements Serializable {

    private Long transactionId;
    private BigDecimal amount;
    private LocalDateTime created;
    private LocalDateTime creator;
    private BigDecimal balance;
    private BigDecimal fee;
    private String status;
    private String transactionDestinationId;
    private TransactionStatusType transactionStatusType;
    private Long userId;
}
