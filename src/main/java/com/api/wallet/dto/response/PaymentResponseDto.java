package com.api.wallet.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PaymentResponseDto {

    private RequestInfoDTO requestInfo;
    private PaymentInfoDTO paymentInfo;
}
