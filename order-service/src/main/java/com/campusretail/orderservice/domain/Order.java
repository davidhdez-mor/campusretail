package com.campusretail.orderservice.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

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
	
	public Order() {
		
	}

	public Order(Long id, LocalDate orderedDate, Status status, BigDecimal total, User user, Cart cart) {
		this.id = id;
		this.orderedDate = orderedDate;
		this.status = status;
		this.total = total;
		this.user = user;
		this.cart = cart;
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
}
