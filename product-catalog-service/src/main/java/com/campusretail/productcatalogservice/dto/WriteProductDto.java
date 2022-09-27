package com.campusretail.productcatalogservice.dto;

import java.math.BigDecimal;
import java.util.Set;


/**
 * Product like class used as DTO 
 * to only ask for the necessary 
 * information to the user
 */
public class WriteProductDto {

	private Long id;
	private String productName;
	private BigDecimal price;
	private String description;
	private boolean availability;
	private Set<ReadCategoryDto> categoryList;

	public WriteProductDto() {
	}

	public WriteProductDto(Long id, String productName, BigDecimal price, String description, Set<ReadCategoryDto> categoryList, boolean availability) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.categoryList = categoryList;
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

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public Set<ReadCategoryDto> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(Set<ReadCategoryDto> categoryList) {
		this.categoryList = categoryList;
	}
}
