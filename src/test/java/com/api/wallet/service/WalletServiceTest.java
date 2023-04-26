package com.api.wallet.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import com.api.wallet.service.WalletService;
import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.api.wallet.dto.request.WalletTransactionRequestDto;
import com.api.wallet.dto.response.WalletTransactionResponseDto;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    private WalletService walletService;

    @BeforeEach
    void setUp() {
        when(webClientBuilder.baseUrl(any(String.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(any(String.class), any(String.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        //walletService = new WalletService(webClientBuilder);
    }

    @Test
    void createWalletTransaction_returnsValidResponse() {
        // Arrange
        WalletTransactionRequestDto requestDto = new WalletTransactionRequestDto();
        requestDto.setAmount(new BigDecimal(100));
        requestDto.setUserId(1000L);

        WalletTransactionResponseDto responseDto = WalletTransactionResponseDto.builder()
                .walletTransactionId(123L)
                .amount(requestDto.getAmount())
                .userId(requestDto.getUserId())
                .build();

        ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);
        ClientHttpResponse response = mock(ClientHttpResponse.class);

        // Act
        WalletTransactionResponseDto result = walletService.createWalletTransaction(requestDto);

        // Assert
        assertEquals(responseDto, result);
    }
}