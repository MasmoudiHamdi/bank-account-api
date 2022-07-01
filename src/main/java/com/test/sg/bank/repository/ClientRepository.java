package com.test.sg.bank.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.sg.bank.entity.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

	Optional<Client> findBySurnameOrEmail(String usernameOrEmail, String usernameOrEmail1);
}
