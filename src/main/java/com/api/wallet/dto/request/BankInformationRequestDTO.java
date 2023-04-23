package com.api.wallet.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankInformationRequestDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String nationalId;
    private String accountNumber;
    private String routingNumber;
    private String bankName;
}
