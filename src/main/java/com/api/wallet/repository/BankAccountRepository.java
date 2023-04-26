package com.api.wallet.repository;

import com.api.wallet.entity.BankAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>,JpaSpecificationExecutor<BankAccount> {

    Optional<BankAccount> findBankAccountByAccountNumber(String bankAccount);
}
