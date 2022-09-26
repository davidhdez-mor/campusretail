package com.campusretail.userservice.dto;

public class WriteUserDto {
	
	private Long roleId;
	private String userName;
	private String userPassword;

	public WriteUserDto() {

	}

	public WriteUserDto(Long roleId, String userName, String userPassword) {
		this.roleId = roleId;
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
