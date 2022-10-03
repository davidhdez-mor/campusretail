package com.campusretail.productcatalogservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.campusretail.productcatalogservice.entity.Product;

/**
 * Interface to declare all the requested
 * methods from the implementation of the 
 * Product service and doing it async
 */
public interface ProductService {
    CompletableFuture<List<Product>> getAllProduct();
    CompletableFuture<List<Product>> getAllProductByCategory(String category);
    CompletableFuture<Product> getProductById(Long id);
    CompletableFuture<List<Product>> getAllProductsByName(String name);
    CompletableFuture<Product> saveProduct(Product product);
    CompletableFuture<Product> deleteProduct(Long productId);
}
