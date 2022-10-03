package com.campusretail.orderservice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Cart entity class with all the
 * needed attributes and configuration
 * for the database insertion
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	private Long id;
	@Column(name = "user_name")
	@NotNull
	private String userName;

	public User() {

	}

	public User(Long id, String userName) {
		this.id = id;
		this.userName = userName;
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
}
