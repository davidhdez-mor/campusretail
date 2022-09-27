package com.campusretail.productcatalogservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;


/**
 * Entity class Category with its 
 * attributes and configurations for
 * the database
 */
@Entity(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	@Column(name = "category", unique = true)
	@NotNull
	@Size(min = 5, max = 50)
	private String category;

	public Category() {
		
	}

	public Category(Long id, String category, List<Product> products) {
		this.id = id;
		this.category = category;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Category)) return false;
		Category desiredCategory = (Category) o;
		return Objects.equals(category, desiredCategory.category);
	}
}
