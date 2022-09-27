package com.campusretail.productcatalogservice.service;

import com.campusretail.productcatalogservice.entity.Category;
import com.campusretail.productcatalogservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository repository;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository repository) {
		this.repository = repository;
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<Category> getCategoryById(Long id) {
		return CompletableFuture.completedFuture(this.repository.getReferenceById(id));
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<Category> addCategory(Category category) {
		return CompletableFuture.completedFuture(this.repository.save(category));
	}

	@Override
	public void deleteCategory(Long id) {
		this.repository.deleteById(id);
	}
}
