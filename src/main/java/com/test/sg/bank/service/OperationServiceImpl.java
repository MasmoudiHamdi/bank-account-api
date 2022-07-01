package com.test.sg.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.sg.bank.entity.Account;
import com.test.sg.bank.entity.Operation;
import com.test.sg.bank.model.NewOperationRequest;
import com.test.sg.bank.model.OperationEvaluation;
import com.test.sg.bank.repository.OperationRepository;

import java.util.List;
import java.util.function.DoubleFunction;

@Service("operation_service")
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private AccountService accountService;

    private static final DoubleFunction<OperationEvaluation> evaluateOperationType = operation -> operation > 0 ? 
    		OperationEvaluation.DEPOSIT : OperationEvaluation.WITHDRAWAL;

    @Transactional
    public Operation registerOperation(NewOperationRequest newOperationRequest, Account account) {
        accountService.updateAccountPosition(account);
        Double operationAmount = newOperationRequest.getOperationAmount();
        Operation operation = new Operation(evaluateOperationType.apply(operationAmount), operationAmount, account);
        return operationRepository.save(operation);
    }

    public List<Operation> getAllOperationForAccount(Account account) {
        return operationRepository.findByAccount(account);
    }

}

