package com.test.sg.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.sg.bank.entity.Account;
import com.test.sg.bank.repository.AccountRepository;

import java.util.Optional;

@Service("account_service")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public void updateAccountPosition(Account account) {
		accountRepository.save(account);
	}

	public Optional<Account> getAccount(int accountNumber) {
		return accountRepository.getAccountByAccountNumber(accountNumber);
	}
}
