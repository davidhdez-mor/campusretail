package com.campusretail.orderservice.domain;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Order entity class with all the 
 * needed attributes and configuration
 * for the database insertion
 */
@Entity
@Table (name = "orders")
public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "ordered_date")
    @NotNull
    private LocalDate orderedDate;

    @Column(name = "status")
    @NotNull
    private Status status;
		
    @Column (name = "total")
    private BigDecimal total;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

	@ManyToOne
	@JoinColumn (name = "cart_id")
	private Cart cart;

	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	//@JoinTable (name = "cart" , joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn (name = "item_id"))
	private List<Item> items;
	
	public Order() {
		
	}

	public Order(Long id, LocalDate orderedDate, Status status, BigDecimal total, User user, Cart cart, List<Item> items) {
		this.id = id;
		this.orderedDate = orderedDate;
		this.status = status;
		this.total = total;
		this.user = user;
		this.cart = cart;
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(LocalDate orderedDate) {
		this.orderedDate = orderedDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
