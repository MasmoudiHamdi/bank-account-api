package com.test.sg.bank.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.sg.bank.BankAccountKataApplication;
import com.test.sg.bank.entity.Account;
import com.test.sg.bank.entity.Client;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BankAccountKataApplication.class })
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    private static Integer ACCOUNT_NUMBER = 1008;
    
    private static boolean setUpIsDone = false;

    @Before
    public void setUp() {
    	
    	if (setUpIsDone) {
            return;
        }
    	
        Client client = new Client("Test", "Test", 2008, "email", "pwd");
        Account account = new Account(ACCOUNT_NUMBER, 100d, client);
        clientRepository.save(client);
        accountRepository.save(account);
        setUpIsDone = true;
    }

    @Test
    public void should_get_account_for_a_known_account_number() {
        Assertions.assertEquals(accountRepository.getAccountByAccountNumber(ACCOUNT_NUMBER).isPresent(), true);
    }

    @Test//(expected = AuthenticationException.class)
    public void should_throw_error_for_an_unknown_account() {
        Assertions.assertEquals(accountRepository.getAccountByAccountNumber(123456).isPresent(), false);
    }

}

