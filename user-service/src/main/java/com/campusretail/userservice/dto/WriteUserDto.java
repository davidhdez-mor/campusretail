package com.campusretail.userservice.dto;

public class WriteUserDto {
	
	private Long roleId;
	
	private Long detailsId;
	private String userName;
	private String userPassword;

	public WriteUserDto() {

	}

	public WriteUserDto(Long roleId, Long detailsId, String userName, String userPassword) {
		this.roleId = roleId;
		this.detailsId = detailsId;
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

	public void setUserPassword(String userName) {
		this.userPassword = userPassword;
	}

	public Long getDetailsId() {
		return detailsId;
	}

	public void setDetailsId(Long detailsId) {
		this.detailsId = detailsId;
	}
}
