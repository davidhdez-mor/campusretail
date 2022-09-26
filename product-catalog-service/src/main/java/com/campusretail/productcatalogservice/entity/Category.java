package com.campusretail.productcatalogservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "category")
	@NotNull
	private String category;
	
	@ManyToMany(mappedBy = "category")
	private List<Product> products;

	public Category() {
		
	}

	public Category(Long id, String category, List<Product> products) {
		this.id = id;
		this.category = category;
		this.products = products;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
