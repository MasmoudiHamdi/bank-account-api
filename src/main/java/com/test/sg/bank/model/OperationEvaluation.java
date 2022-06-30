package com.test.sg.bank.model;

public enum OperationEvaluation {
	DEPOSIT("Deposit"), WITHDRAWAL("withdrawal");

	private String value;

	OperationEvaluation(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
