package com.campusretail.productcatalogservice.service;

import com.campusretail.productcatalogservice.entity.Product;
import com.campusretail.productcatalogservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    
    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<List<Product>> getAllProduct() {
        return CompletableFuture.completedFuture(repository.findAll());
    }

    @Override
    public CompletableFuture<List<Product>> getAllProductByCategory(String category) {
        return CompletableFuture.completedFuture(this.repository.findAll()
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
    public CompletableFuture<Product> getProductById(Long id) {
        return CompletableFuture.completedFuture(repository.findById(id).orElse(null));
    }

    @Override
    public CompletableFuture<List<Product>> getAllProductsByName(String name) {
        return CompletableFuture.completedFuture(repository.findAllByProductName(name));
    }

    @Override
    public  CompletableFuture<Product> addProduct(Product product) {
        return CompletableFuture.completedFuture(repository.save(product));
    }

    @Override
    public void deleteProduct(Long productId) {
        repository.deleteById(productId);
    }
}
