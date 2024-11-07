package com.example.demo.user.Errors;

import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

public class ErrorUser {

	private String msg;
	private Integer status;
	private String cause;

	public ErrorUser(String msg, Integer status, String cause) {
		this.cause = cause;
		this.status = status;
		this.msg = msg;
	}

	public ErrorUser(String string, HttpStatus internalServerError) {
		// TODO Auto-generated constructor stub
	}

	public String build(ErrorUser errorUser) {
		Gson gson = new Gson();
		String json = gson.toJson(errorUser);
		return json;
	}
}
