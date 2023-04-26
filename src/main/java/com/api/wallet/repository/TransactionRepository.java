package com.api.wallet.repository;

import com.api.wallet.entity.Transaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaSpecificationExecutor<Transaction>, CrudRepository<Transaction, Long>  {

}
