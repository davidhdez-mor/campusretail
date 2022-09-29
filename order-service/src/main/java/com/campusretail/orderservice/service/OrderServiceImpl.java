package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Order;
import com.campusretail.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of all the 
 * methods from the interface
 * of order to do all the actions
 * needed to make work the 
 * controller
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl (OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public CompletableFuture<Order> saveOrder(Order order) {
        return CompletableFuture.completedFuture(orderRepository.save(order));
    }
}
