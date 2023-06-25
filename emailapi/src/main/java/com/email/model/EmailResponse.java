package com.email.model;

public class EmailResponse {
	public EmailResponse(String token) {
		super();
		this.token = token;
	}

	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}
