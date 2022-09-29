package com.campusretail.orderservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.campusretail.orderservice.domain.Product;

/**
 * Configuration class for the feign
 * product service for the API
 */
@FeignClient(name = "product-catalog-service")
public interface ProductClient {

    @GetMapping(value = "/products/{id}")
    Product getProductById(@PathVariable Long id);

}
