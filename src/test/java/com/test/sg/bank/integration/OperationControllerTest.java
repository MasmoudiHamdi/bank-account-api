package com.test.sg.bank.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.test.sg.bank.BankAccountKataApplication;
import com.test.sg.bank.entity.Account;
import com.test.sg.bank.entity.Client;
import com.test.sg.bank.entity.Operation;
import com.test.sg.bank.model.NewOperationRequest;
import com.test.sg.bank.model.OperationEvaluation;
import com.test.sg.bank.service.AccountService;
import com.test.sg.bank.service.OperationService;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { BankAccountKataApplication.class })
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class OperationControllerTest {

    @MockBean
    private OperationService operationService;

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    private Client client = new Client("Test", "Test", 1, "email","pwd");

    private static final Integer ACCOUNT_NUMBER = 1001;
    private static final Integer BAD_ACCOUNT_NUMBER = 888;
    private static final String DEPOSIT_OPERATION = OperationEvaluation.DEPOSIT.getValue();
    private static final String WITHDRAWAL_OPERATION = OperationEvaluation.WITHDRAWAL.getValue();


    @Test
    public void should_execute_operation() throws Exception {
        Account account = new Account(ACCOUNT_NUMBER,50d, client);
        NewOperationRequest newOperationRequest = new NewOperationRequest(ACCOUNT_NUMBER,50d);
        Operation operation = new Operation(DEPOSIT_OPERATION, 50d, account);

        when(accountService.getAccount(ACCOUNT_NUMBER))
                .thenReturn(java.util.Optional.of(account));
        when(operationService.registerOperation(newOperationRequest, account))
                .thenReturn(operation);

        String json = "{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\",\"operationAmount\":50}";
        this.mockMvc.perform(post("/operations/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    public void should_not_execute_operation_with_invalid_arguments() throws Exception {
        String json = "{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\"}";
        this.mockMvc.perform(post("/operations/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void should_not_execute_withdrawal_operation_with_insufficient_fund() throws Exception {
        Account account = new Account(ACCOUNT_NUMBER,20d, client);
        NewOperationRequest newOperationRequest = new NewOperationRequest(ACCOUNT_NUMBER,-50d);
        Operation operation = new Operation(WITHDRAWAL_OPERATION, -50d, account);

        when(accountService.getAccount(ACCOUNT_NUMBER))
                .thenReturn(java.util.Optional.of(account));
        when(operationService.registerOperation(newOperationRequest, account))
                .thenReturn(operation);

        String json = "{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\",\"operationAmount\":-50}";
        this.mockMvc.perform(post("/operations/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void should_get_operations_history_for_account() throws Exception{
        Account account = new Account(ACCOUNT_NUMBER,20d, client);
        Operation operation = new Operation(DEPOSIT_OPERATION, 100d, account);
        List<Operation> operationHistory = new ArrayList<>();
        operationHistory.add(operation);

        when(accountService.getAccount(ACCOUNT_NUMBER))
                .thenReturn(java.util.Optional.of(account));
        when(operationService.getAllOperationForAccount(account))
                .thenReturn(operationHistory);

        this.mockMvc.perform(get("/operations")
                .param("accountNumber", String.valueOf(ACCOUNT_NUMBER))
        ).andDo(print())
                .andExpect(jsonPath("$[*].operationType").value(OperationEvaluation.DEPOSIT.getValue()))
                .andExpect(jsonPath("$[*].operationValue").value(100d))
                .andExpect(status().isOk());
    }


    @Test
    public void should_return_not_found_when_getting_history_for_an_unknown_account() throws Exception{
        this.mockMvc.perform(get("/operations")
                .param("accountNumber", String.valueOf(BAD_ACCOUNT_NUMBER))
        ).andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void should_return_bad_request_when_executing_operation_for_an_unknown_account() throws Exception{
        String json = "{\"accountNumber\":\"" + BAD_ACCOUNT_NUMBER + "\",\"operationAmount\":50}";
        this.mockMvc.perform(post("/operations/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void should_return_forbidden_when_executing_operation_on_an_unauthorized_resource() throws Exception{
        Account account = new Account(ACCOUNT_NUMBER,50d, client);

        when(accountService.getAccount(ACCOUNT_NUMBER))
                .thenReturn(java.util.Optional.of(account));

        String json = "{\"accountNumber\":\"" + ACCOUNT_NUMBER + "\",\"operationAmount\":50}";
        this.mockMvc.perform(post("/operations/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void should_return_account_for_a_known_account() throws Exception{
        Account account = new Account(ACCOUNT_NUMBER,50d, client);
        when(accountService.getAccount(ACCOUNT_NUMBER))
                .thenReturn(Optional.of(account));

        this.mockMvc.perform(get("/operations/{accountNumber}", ACCOUNT_NUMBER)

        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(".accountPosition").value(50d));
    }

    @Test
    public void should_return_account_not_found_exception_for_an_unknown_account() throws Exception{

        when(accountService.getAccount(BAD_ACCOUNT_NUMBER))
                .thenReturn(Optional.empty());

        this.mockMvc.perform(get("/operations/{accountNumber}", BAD_ACCOUNT_NUMBER)

        ).andDo(print())
                .andExpect(status().isNotFound());
    }
}

