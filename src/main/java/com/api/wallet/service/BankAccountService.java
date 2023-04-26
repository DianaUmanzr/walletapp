package com.api.wallet.service;

import com.api.wallet.dto.request.BankInformationRequestDTO;
import com.api.wallet.entity.BankAccount;
import com.api.wallet.entity.User;
import com.api.wallet.repository.BankAccountRepository;
import com.api.wallet.repository.UserRepository;
import exception.CommonErrors;
import exception.ExceptionUtils;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountService {

	private final BankAccountRepository bankAccountRepository;

    private final UserRepository userRepository;

    public Map saveBankAccount(BankInformationRequestDTO bankInformationRequestDTO) {

        Optional<User> userPersisted = userRepository.findById(bankInformationRequestDTO.getUserId());
        User user = User.builder().build();
        if (userPersisted.isPresent()){
            user = User.builder()
                    .userId(userPersisted.get().getUserId())
                    .name(bankInformationRequestDTO.getFirstName())
                    .surname(bankInformationRequestDTO.getLastName())
                    .nationalId(bankInformationRequestDTO.getNationalId())
                    .build();
        } else {
            ExceptionUtils.throwUserNotFoundException(CommonErrors.USER_001_NOT_FOUND);
        }

        Optional<BankAccount> bankAccountPersisted = bankAccountRepository.findBankAccountByAccountNumber(bankInformationRequestDTO.getAccountNumber());

        if(!bankAccountPersisted.isPresent()) {
            BankAccount bankAccount = BankAccount.builder()
                    .accountNumber(bankInformationRequestDTO.getAccountNumber())
                    .routingNumber(bankInformationRequestDTO.getRoutingNumber())
                    .bankName(bankInformationRequestDTO.getBankName())
                    .user(user).build();
            bankAccountRepository.save(bankAccount);
        } else {
            ExceptionUtils.throwBankAccountExistedException(CommonErrors.BANK_001_EXISTED);

        }
        return Collections.singletonMap("result", true);
    }
    

    public Optional<BankAccount> findOne(Specification<BankAccount> spec) {
		return bankAccountRepository.findOne(spec);
	}

}
