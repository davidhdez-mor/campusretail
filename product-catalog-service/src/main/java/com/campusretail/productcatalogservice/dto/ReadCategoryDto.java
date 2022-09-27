package com.campusretail.productcatalogservice.dto;


public class ReadCategoryDto {
	private String category;

	public ReadCategoryDto() {
		
	}
	
	public ReadCategoryDto(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
