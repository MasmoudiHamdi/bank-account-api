package com.test.sg.bank.exception;

public class InsufficientFundException extends Exception {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientFundException(String accountNumber) {
        super(String.format("operation refused, insufficient fund for %s.", accountNumber));
    }
}

