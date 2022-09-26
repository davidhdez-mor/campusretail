package com.campusretail.productcatalogservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_name")
	@NotNull
	private String productName;

	@Column(name = "price")
	@NotNull
	private BigDecimal price;

	@Column(name = "description")
	private String description;

	@ManyToMany
	@JoinColumn(name = "category_id")
	private List<Category> category;

	@Column(name = "availability")
	@NotNull
	private int availability;

	public Product() {

	}

	public Product(Long id, String productName, BigDecimal price, String description, List<Category> category, int availability) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.category = category;
		this.availability = availability;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}
}