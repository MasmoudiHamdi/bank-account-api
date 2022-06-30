package com.test.sg.bank.exception;

public class AccountNotFoundException extends Exception {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String accountNumber) {
        super(String.format("Account number %s has not been found.", accountNumber));
    }
}

