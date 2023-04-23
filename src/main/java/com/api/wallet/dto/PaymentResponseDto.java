package com.api.wallet.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class PaymentResponseDto {

    private RequestInfoDTO requestInfo;
    private PaymentInfoDTO paymentInfo;
}
