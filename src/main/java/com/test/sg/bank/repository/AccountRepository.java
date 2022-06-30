package com.test.sg.bank.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.test.sg.bank.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.accountPosition = a.accountPosition + :newOperation WHERE a.accountNumber = :accountNumber")
    void updateAccountPosition(@Param("newOperation") double newOperation, @Param("accountNumber") int accountNumber);

    Optional<Account> getAccountByAccountNumber(Integer accountNumber);
}

