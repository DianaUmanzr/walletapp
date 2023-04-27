package com.api.wallet.service;

import com.api.wallet.dto.request.BankInformationRequestDTO;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.User;
import com.api.wallet.repository.BankAccountRepository;
import com.api.wallet.repository.UserRepository;
import exception.BankAccountExistedException;
import exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        bankAccountRepository = mock(BankAccountRepository.class);
        userRepository = mock(UserRepository.class);
        bankAccountService = new BankAccountService(bankAccountRepository, userRepository);
    }

    @Test
    void testSaveBankAccount() {
        BankInformationRequestDTO requestDTO = new BankInformationRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setFirstName("John");
        requestDTO.setLastName("Doe");
        requestDTO.setNationalId("1234567890");
        requestDTO.setAccountNumber("1234567890");
        requestDTO.setRoutingNumber("987654321");

        User user = User.builder()
                .userId(requestDTO.getUserId())
                .name(requestDTO.getFirstName())
                .surname(requestDTO.getLastName())
                .nationalId(requestDTO.getNationalId())
                .build();
        when(userRepository.findById(requestDTO.getUserId())).thenReturn(Optional.of(user));
        when(bankAccountRepository.findBankAccountByAccountNumber(requestDTO.getAccountNumber())).thenReturn(Optional.empty());

        bankAccountService.saveBankAccount(requestDTO);
    }

    @Test
    void testSaveBankAccountUserNotFound() {
        BankInformationRequestDTO requestDTO = new BankInformationRequestDTO();
        requestDTO.setUserId(1L);

        when(userRepository.findById(requestDTO.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> bankAccountService.saveBankAccount(requestDTO));
    }

    @Test
    void testSaveBankAccountBankAccountExisted() {
        BankAccount bankAccount = BankAccount.builder().build();

        BankInformationRequestDTO requestDTO = new BankInformationRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setAccountNumber("1234567890");

        User user = User.builder()
                .userId(requestDTO.getUserId())
                .build();
        when(userRepository.findById(requestDTO.getUserId())).thenReturn(Optional.of(user));
        when(bankAccountRepository.findBankAccountByAccountNumber(requestDTO.getAccountNumber())).thenReturn(Optional.of(bankAccount));

        assertThrows(BankAccountExistedException.class, () -> bankAccountService.saveBankAccount(requestDTO));
    }

    @Test
    void testFindOne() {
        Specification<BankAccount> spec = mock(Specification.class);
        BankAccount bankAccount = BankAccount.builder().build();
        Optional<BankAccount> expectedBankAccount = Optional.of(bankAccount);
        when(bankAccountRepository.findOne(spec)).thenReturn(expectedBankAccount);

        Optional<BankAccount> actualBankAccount = bankAccountService.findOne(spec);
        assertEquals(expectedBankAccount, actualBankAccount);
    }
}
