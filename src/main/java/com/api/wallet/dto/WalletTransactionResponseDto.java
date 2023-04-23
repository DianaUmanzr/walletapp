package com.api.wallet.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class WalletTransactionResponseDto {
    private Long wallet_transaction_id;
    private BigDecimal amount;
    private Long user_id;
}
