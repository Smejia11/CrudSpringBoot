package com.example.demo.user;

public class DtoUser {
	private String firstName;
	private String lastName;
	private String email;
	private User user;

	public DtoUser() {

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User build() {
		User userModel = new User();
		userModel.setEmail(email);
		userModel.setFirstName(firstName);
		userModel.setLastName(lastName);
		return userModel;
	}

	public User build(User userModel) {
		userModel.setEmail(email);
		userModel.setFirstName(firstName);
		userModel.setLastName(lastName);
		return userModel;
	}

}
