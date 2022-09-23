package com.campusretail.orderservice.service;

import com.campusretail.orderservice.domain.Order;

public interface OrderService {
    public Order saveOrder(Order order);
}
