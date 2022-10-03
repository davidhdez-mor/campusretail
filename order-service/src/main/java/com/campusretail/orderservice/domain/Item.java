package com.campusretail.orderservice.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Item entity class with all the 
 * needed attributes and configuration
 * for the database insertion
 */
@Entity
@Table (name = "items")
public class Item {
	
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    //@JsonIgnore
    private Long id;

    @Column (name = "quantity")
    @NotNull
    private int quantity;


	@Column (name = "subtotal")
    @NotNull
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product product;

    public Item() {
    	
    }

	public Item(int quantity, BigDecimal subTotal, Product product) {
		this.quantity = quantity;
		this.subTotal = subTotal;
		this.product = product;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
