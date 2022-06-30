package com.test.sg.bank.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.sg.bank.entity.Account;
import com.test.sg.bank.entity.Client;
import com.test.sg.bank.entity.Operation;
import com.test.sg.bank.model.NewOperationRequest;
import com.test.sg.bank.model.OperationEvaluation;
import com.test.sg.bank.repository.OperationRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class OperationServiceTest {

    @Autowired
    private OperationService operationService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private OperationRepository operationRepository;

    private Client client = new Client("Test", "Test", 1, "email","pwd");

    @Test
    public void should_register_operation() {
        Account account = new Account(1001,50d, client);
        Operation expectedOperation = new Operation(OperationEvaluation.DEPOSIT.getValue(), 50d, account);
        NewOperationRequest newOperationRequest = new NewOperationRequest(1001, 50d);

        doNothing()
                .when(accountService)
                .updateAccountPosition(account);
        when(operationRepository.save(any()))
                .thenReturn(expectedOperation);

        Operation insertedOperation = operationService.registerOperation(newOperationRequest, account);

        Assertions.assertEquals(insertedOperation, expectedOperation);
    }

}
