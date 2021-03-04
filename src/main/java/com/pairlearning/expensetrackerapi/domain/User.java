package com.pairlearning.expensetrackerapi.domain;

public class User {
	final private Integer userId;
	final private String firstName;
	final private String lastName;
	final private String email;
	final private String password;
	
	public User(Integer userId, String firstName, String lastName, String email, String password) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	
}
