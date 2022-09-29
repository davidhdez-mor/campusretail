package com.campusretail.userservice.dto;


public class UserLoginDto {
	private String login;
	private String token;
	
	public UserLoginDto() {
		
	}

	public UserLoginDto(String login, String token) {
		this.login = login;
		this.token = token;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
