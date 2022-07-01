package com.test.sg.bank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.test.sg.bank.entity.Account;
import com.test.sg.bank.entity.Client;
import com.test.sg.bank.exception.AccountNotFoundException;
import com.test.sg.bank.model.RoleName;
import com.test.sg.bank.repository.AccountRepository;

import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    private static Integer ACCOUNT_NUMBER = 1008;
    
    
    private Client client = new Client("Test", "Test", 2008, "email", "pwd", RoleName.ROLE_USER);
    private Account account = new Account(ACCOUNT_NUMBER, 100d, client);


    @Test
    public void should_get_account_position_for_account_number() throws AccountNotFoundException {
        when(accountRepository.getAccountByAccountNumber(ACCOUNT_NUMBER))
                .thenReturn(Optional.of(account));
        Assertions.assertEquals(accountService.getAccount(ACCOUNT_NUMBER).isPresent(), true);
    }

    @Test
    public void should_throw_account_not_found_when_trying_to_get_account_position_for_unknown_account_number() throws AccountNotFoundException {
        when(accountRepository.getAccountByAccountNumber(ACCOUNT_NUMBER))
                .thenReturn(Optional.empty());
        Assertions.assertEquals(accountService.getAccount(ACCOUNT_NUMBER).isPresent(), false);
    }

}
