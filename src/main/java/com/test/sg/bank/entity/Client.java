package com.test.sg.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.sg.bank.model.RoleName;

import javax.persistence.*;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	@Column(name = "client_id")
	private Integer clientId;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "surname")
	private String surname;

	@Column(name = "client_number")
	private Integer clientNumber;

	@Column(name = "email")
	private String email;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private RoleName role;

	public Client() {
		super();
	}

	public Client(String firstname, String surname, Integer clientNumber, String email, String password,
			RoleName role) {
		this.firstname = firstname;
		this.surname = surname;
		this.clientNumber = clientNumber;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Integer getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(Integer clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleName getRole() {
		return role;
	}

	public void setRole(RoleName role) {
		this.role = role;
	}

}
