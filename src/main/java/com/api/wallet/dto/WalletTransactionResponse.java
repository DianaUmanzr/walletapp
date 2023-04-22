package com.api.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class WalletTransactionResponse {

    private String transactionId;
    private String sourceWalletId;
    private String destinationWalletId;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
}
