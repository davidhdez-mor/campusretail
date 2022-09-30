package com.campusretail.orderservice.controller;

import com.campusretail.orderservice.domain.Order;
import com.campusretail.orderservice.exception.OrderNotFoundException;
import com.campusretail.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Controller to manage all the
 * methods for the order of the
 * API
 */
@RestController
public class OrderController {

	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * Endpoint which save the current
	 * state of the cart into the database
	 *
	 * @param userId cart id which is going
	 *               to be saved in the database
	 * @return an order to show the comitted
	 * changes into the database
	 */
	@PostMapping(value = "/order")
	public ResponseEntity<Order> saveOrder(@RequestHeader("X-auth-user-id") Long userId) throws InterruptedException, ExecutionException {
		Order order = orderService.saveOrder(userId).get();
		if (order != null)
			return new ResponseEntity<>(order, HttpStatus.CREATED);
		throw new OrderNotFoundException("Ordernot found");
	}
	
	@GetMapping("/order")
	public ResponseEntity<List<Order>> getOrders(@RequestHeader("X-auth-user-id") Long userId) throws ExecutionException, InterruptedException {
		List<Order> orders = orderService.getOrders(userId).get();
		if (!orders.isEmpty())
			return new ResponseEntity<>(orders, HttpStatus.OK);
		throw new OrderNotFoundException("Orders not found");
	}
	
	@GetMapping("/order/{id}")
	public ResponseEntity<Order> getOrder(@PathVariable Long id, @RequestHeader("X-auth-user-id") Long userId) throws ExecutionException, InterruptedException {
		Order order = orderService.getOrder(id, userId).get();
		if (order != null)
			return new ResponseEntity<>(order, HttpStatus.OK);
		throw new OrderNotFoundException("Order not found");
	}

}

