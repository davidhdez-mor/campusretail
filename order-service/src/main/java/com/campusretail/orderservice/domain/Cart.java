package com.campusretail.orderservice.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Cart entity class with all the
 * needed attributes and configuration
 * for the database insertion
 */
@Entity
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@JoinTable (name = "cart" , joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn (name = "item_id"))
	private List<Item> items;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
//	private Long userId;

	public Cart() {

	}

	public Cart(Long id, List<Item> items, User user) {
		this.id = id;
		this.items = items;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
