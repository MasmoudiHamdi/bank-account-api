package com.test.sg.bank.service;

import java.util.List;

import com.test.sg.bank.entity.Account;
import com.test.sg.bank.entity.Operation;
import com.test.sg.bank.model.NewOperationRequest;

public interface OperationService {

    Operation registerOperation(NewOperationRequest newOperationRequest, Account account);

    List<Operation> getAllOperationForAccount(Account account);
}


