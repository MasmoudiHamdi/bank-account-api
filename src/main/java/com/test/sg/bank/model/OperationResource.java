package com.test.sg.bank.model;

import java.util.Date;

import com.test.sg.bank.entity.Operation;

public class OperationResource {

	private String operationType;
	private Double operationValue;
	private Date date;
	private Double balance;

	public OperationResource() {
		super();
	}

	public OperationResource(Operation operation) {
		this.operationType = operation.getOperationType();
		this.operationValue = operation.getOperationValue();
		this.date = operation.getDate();
		this.balance = operation.getBalance();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((operationType == null) ? 0 : operationType.hashCode());
		result = prime * result + ((operationValue == null) ? 0 : operationValue.hashCode());
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
		OperationResource other = (OperationResource) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (operationType == null) {
			if (other.operationType != null)
				return false;
		} else if (!operationType.equals(other.operationType))
			return false;
		if (operationValue == null) {
			if (other.operationValue != null)
				return false;
		} else if (!operationValue.equals(other.operationValue))
			return false;
		return true;
	}

	public String getOperationType() {
		return operationType;
	}

	public Double getOperationValue() {
		return operationValue;
	}

	public Date getDate() {
		return date;
	}

	public Double getBalance() {
		return balance;
	}

}
