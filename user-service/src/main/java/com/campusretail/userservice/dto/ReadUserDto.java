package com.campusretail.userservice.dto;

public class ReadUserDto {
	private Long roleId;
	private String userName;
	
	private String roleName;

	public ReadUserDto() {

	}

	public ReadUserDto(Long roleId, String userName, String roleName) {
		this.roleId = roleId;
		this.userName = userName;
		this.roleName = roleName;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
