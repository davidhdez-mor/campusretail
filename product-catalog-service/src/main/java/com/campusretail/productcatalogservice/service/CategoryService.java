package com.campusretail.productcatalogservice.service;

import com.campusretail.productcatalogservice.entity.Category;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interface to declare all the requested
 * methods from the implementation of the 
 * Category service and doing it async
 */
public interface CategoryService {
	CompletableFuture<List<Category>> getCategories();
	CompletableFuture<Category> getCategoryById(Long id);
	CompletableFuture<Category> saveCategory(Category category);
	void deleteCategory(Long id);
}
