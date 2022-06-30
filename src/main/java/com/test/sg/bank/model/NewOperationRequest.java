package com.test.sg.bank.model;

import javax.validation.constraints.NotNull;

public final class NewOperationRequest {

	@NotNull
	private Integer accountNumber;

	@NotNull
	private Double operationAmount;

	public NewOperationRequest() {
		super();
	}

	public NewOperationRequest(Integer accountNumber, Double operationAmount) {
		this.accountNumber = accountNumber;
		this.operationAmount = operationAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((operationAmount == null) ? 0 : operationAmount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewOperationRequest other = (NewOperationRequest) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (operationAmount == null) {
			if (other.operationAmount != null)
				return false;
		} else if (!operationAmount.equals(other.operationAmount))
			return false;
		return true;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public Double getOperationAmount() {
		return operationAmount;
	}
}
