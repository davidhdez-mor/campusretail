package com.campusretail.productcatalogservice.service;

import com.campusretail.productcatalogservice.entity.Category;

import java.util.concurrent.CompletableFuture;

public interface CategoryService {
	CompletableFuture<Category> getCategoryById(Long id);
	CompletableFuture<Category> addCategory(Category category);
	void deleteCategory(Long id);
}
