package com.test.sg.bank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.sg.bank.entity.Account;
import com.test.sg.bank.entity.Operation;

import java.util.List;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Integer> {

    List<Operation> findByAccount(Account account);

}

