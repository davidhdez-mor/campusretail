package com.campusretail.userservice.dto;

/**
 * DTO class to only request the escential
 * information when we want to create a user
 */

public class WriteUserDto {
	
	private Long roleId;
	private String userName;
	private String userPassword;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String street;
	private String streetNumber;
	private String zipCode;
	private String locality;
	private String country;

	public WriteUserDto() {

	}

	public WriteUserDto(Long roleId,
	                    String userName,
	                    String userPassword,
	                    String firstName,
	                    String lastName,
	                    String email,
	                    String phoneNumber,
	                    String street,
	                    String streetNumber,
	                    String zipCode,
	                    String locality,
	                    String country) {
		this.roleId = roleId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.locality = locality;
		this.country = country;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
