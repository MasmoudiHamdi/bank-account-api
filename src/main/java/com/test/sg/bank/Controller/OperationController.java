package com.test.sg.bank.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.sg.bank.entity.Account;
import com.test.sg.bank.entity.Operation;
import com.test.sg.bank.exception.AccountNotFoundException;
import com.test.sg.bank.exception.InsufficientFundException;
import com.test.sg.bank.model.NewOperationRequest;
import com.test.sg.bank.service.AccountService;
import com.test.sg.bank.service.OperationService;
import com.test.sg.bank.model.OperationResource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/operations")

public class OperationController {

	@Autowired
	private OperationService operationService;

	@Autowired
	private AccountService accountService;

	private static final Logger LOGGER = LogManager.getLogger(OperationController.class);

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Operation> executeOperation(@Validated @RequestBody NewOperationRequest newOperationRequest)
			throws AccountNotFoundException, InsufficientFundException {

		Optional<Account> optionalAccount = accountService.getAccount(newOperationRequest.getAccountNumber());

		if (!optionalAccount.isPresent()) {
			throw new AccountNotFoundException(String.valueOf(newOperationRequest.getAccountNumber()));
		}

		Account account = optionalAccount.get();

		Double newAccountPosition = account.getAccountPosition() + newOperationRequest.getOperationAmount();

		if (newAccountPosition < 0) {
			throw new InsufficientFundException(String.valueOf(newOperationRequest.getAccountNumber()));
		}

		account.setAccountPosition(newAccountPosition);
		LOGGER.info("Account {} position updated", account.getAccountNumber());
		Operation operation = operationService.registerOperation(newOperationRequest, account);
		LOGGER.info("New Operation registered for account {}", account.getAccountNumber());

		return new ResponseEntity<>(operation, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<OperationResource>> getAllOperationForAClient(
			@RequestParam(value = "accountNumber", required = true) int accountNumber) throws AccountNotFoundException {

		LOGGER.info("getAllOperationForAClient() accountNumber: {}", accountNumber);

		Optional<Account> optionalAccount = accountService.getAccount(accountNumber);
		if (!optionalAccount.isPresent()) {
			throw new AccountNotFoundException(String.valueOf(accountNumber));
		}
		Account account = optionalAccount.get();

		List<Operation> operationHistory = operationService.getAllOperationForAccount(account);

		List<OperationResource> operationResources = new ArrayList<>();

		operationHistory.stream().forEach(op -> {
			operationResources.add(new OperationResource(op));
		});

		return ResponseEntity.status(HttpStatus.OK).body(operationResources);
	}

}
