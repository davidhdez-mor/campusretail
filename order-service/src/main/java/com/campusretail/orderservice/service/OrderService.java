package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Order;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interface with all the needed
 * methods for the implementation
 * of Cart class
 */
public interface OrderService {
    CompletableFuture<Order> saveOrder(Long userId);
    CompletableFuture<List<Order>> getOrders(Long userId);
    CompletableFuture<Order> getOrder(Long id, Long userId);
}
