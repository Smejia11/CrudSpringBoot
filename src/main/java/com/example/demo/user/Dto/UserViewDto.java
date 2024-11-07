/**
 * 
 */
package com.example.demo.user.Dto;

import com.example.demo.user.Model.User;

/**
 * 
 */
public class UserViewDto {

	private String firstName;
	private String lastName;
	private String Email;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}

	public UserViewDto(User userModel) {
		this.setEmail(userModel.getEmail());
		this.setFirstName(userModel.getFirstName());
		this.setLastName(userModel.getLastName());
	}

}
