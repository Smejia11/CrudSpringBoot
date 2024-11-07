package com.example.demo.user.Dto;

import com.example.demo.user.Model.User;
import com.example.demo.user.Utils.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {
	@NotNull
	@Size(min = 1, message = "{Size.userDto.firstName}")
	private String firstName;
	@NotNull
	@Size(min = 1, message = "{Size.userDto.lastName}")
	private String lastName;
	private String email;
	@NotNull
	@Size(min = 6, message = "{Size.userDto.password}")
	private String password;

	public UserDto() {

	}

	public String getPassword() {
		return password;
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
		String encryptPass = PasswordUtil.encryptPassword(password);
		userModel.setPassword(encryptPass);
		return userModel;
	};

	public User build(User userModel) {
		userModel.setEmail(email);
		userModel.setFirstName(firstName);
		userModel.setLastName(lastName);
		Boolean encryptPassNew = PasswordUtil.verifyPassword(password, userModel.getPassword());
		if (!encryptPassNew) {
			userModel.setPassword(PasswordUtil.encryptPassword(password));
		}
		return userModel;
	}

}
