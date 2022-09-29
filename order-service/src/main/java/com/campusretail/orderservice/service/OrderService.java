package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Order;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Interface with all the needed
 * methods for the implementation
 * of Cart class
 */
public interface OrderService {
    CompletableFuture<Optional<Order>> saveOrder(Long cartId);
}
