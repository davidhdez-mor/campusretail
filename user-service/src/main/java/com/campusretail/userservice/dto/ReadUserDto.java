package com.campusretail.userservice.dto;

public class ReadUserDto {
	private Long id;
	private String userName;
	
	private String roleName;

	public ReadUserDto() {

	}

	public ReadUserDto(Long id, String userName, String roleName) {
		this.id = id;
		this.userName = userName;
		this.roleName = roleName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
