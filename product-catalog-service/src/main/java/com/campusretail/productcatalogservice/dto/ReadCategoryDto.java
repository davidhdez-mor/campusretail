package com.campusretail.productcatalogservice.dto;

/**
 * Category like class used as DTO 
 * to show only the wanted information 
 * to the user
 */
public class ReadCategoryDto {
	private String categoryName;

	public ReadCategoryDto() {
		
	}
	
	public ReadCategoryDto(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategory() {
		return categoryName;
	}

	public void setCategory(String category) {
		this.categoryName = category;
	}
}
