package com.campusretail.productcatalogservice.service;

import com.campusretail.productcatalogservice.entity.Category;
import com.campusretail.productcatalogservice.entity.Product;
import com.campusretail.productcatalogservice.repository.CategoryRepository;
import com.campusretail.productcatalogservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


/**
 * Implementation of the Product service
 * interface to develop all the necessary methods
 * and doing it async
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<List<Product>> getAllProduct() {
		return CompletableFuture.completedFuture(productRepository.findAll());
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<List<Product>> getAllProductByCategory(String category) {
		return CompletableFuture.completedFuture(this.productRepository.findAll()
				.stream()
				.filter(product -> product.getCategory()
						.stream()
						.anyMatch(
								cat -> cat.getCategory().equals(category)
						)
				)
				.collect(Collectors.toList()));
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<Product> getProductById(Long id) {
		return CompletableFuture.completedFuture(productRepository.findById(id).orElse(null));
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<List<Product>> getAllProductsByName(String name) {
		return CompletableFuture.completedFuture(productRepository.findAllByProductName(name));
	}

	@Override
	@Async("AsyncExecutor")
	public CompletableFuture<Product> saveProduct(Product product) {
		Set<Category> productCategories = product.getCategory();
		Set<Category> categories = categoryRepository.findAll()
				.stream()
				.filter(category -> 
						productCategories
								.stream()
								.anyMatch(category::equals)
				)
				.collect(Collectors.toSet());
		if (categories.isEmpty()) categories.add(categoryRepository.findById(1L).orElse(null));
		
		product.setCategory(categories);
		return CompletableFuture.completedFuture(productRepository.save(product));
	}

	@Override
	public void deleteProduct(Long productId) {
		productRepository.deleteById(productId);
	}
}
