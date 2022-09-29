package com.campusretail.orderservice.controller;

import com.campusretail.orderservice.domain.Item;
import com.campusretail.orderservice.domain.Order;
import com.campusretail.orderservice.domain.User;
import com.campusretail.orderservice.exception.OrderNotFoundException;
import com.campusretail.orderservice.feignclient.UserClient;
import com.campusretail.orderservice.service.CartService;
import com.campusretail.orderservice.service.OrderService;
import com.campusretail.orderservice.utilities.OrderUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Controller to manage all the
 * methods for the order of the
 * API
 */
@RestController
public class OrderController {

	private final UserClient userClient;

	private final OrderService orderService;

	private final CartService cartService;


	@Autowired
	public OrderController(UserClient userClient, OrderService orderService, CartService cartService) {
		this.userClient = userClient;
		this.orderService = orderService;
		this.cartService = cartService;
	}

	/**
	 * Endpoint which save the current
	 * state of the cart into the database
	 *
	 * @param userId the id of the cart owner
	 * @param cartId the cart id which is going
	 *               to be saved in the database
	 * @return an order to show the comitted
	 * changes into the database
	 */
	@PostMapping(value = "/order/{userId}")
	public ResponseEntity<Order> saveOrder(
			@PathVariable Long userId,
			@RequestHeader(value = "Cookie") Long cartId) throws InterruptedException, ExecutionException {

		Optional<Order> order = orderService.saveOrder(cartId).get();
		if (order.isPresent())
			return new ResponseEntity<>(order.get(), HttpStatus.CREATED);
		throw new OrderNotFoundException("Order with id " + cartId + " not found");
	}

}

