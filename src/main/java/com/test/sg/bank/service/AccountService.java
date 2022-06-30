package com.test.sg.bank.service;

import java.util.Optional;

import com.test.sg.bank.entity.Account;

public interface AccountService {

    void updateAccountPosition(Account account);

    Optional<Account> getAccount(int accountNumber);
}

