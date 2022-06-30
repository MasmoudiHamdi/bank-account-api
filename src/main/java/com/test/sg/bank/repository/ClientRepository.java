package com.test.sg.bank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.sg.bank.entity.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

}

