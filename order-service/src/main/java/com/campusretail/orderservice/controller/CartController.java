package com.campusretail.orderservice.controller;

import com.campusretail.orderservice.domain.Cart;
import com.campusretail.orderservice.domain.Item;
import com.campusretail.orderservice.exception.CartNotFoundException;
import com.campusretail.orderservice.exception.UnableToAddItemException;
import com.campusretail.orderservice.exception.UnableToRemoveItemException;
import com.campusretail.orderservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Controller which manage
 * all the actions for the
 * cart of the API
 */
@RestController
public class CartController {

	private final CartService cartService;

	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	/**
	 * Endpoint which returns the current cart
	 * in the API (All the items in the car as a
	 * collection)
	 *
	 * @param userId the id of the cart in the API
	 * @return a collection of all the items of the cart
	 */
	@GetMapping(value = "/cart")
	public ResponseEntity<List<Item>> getCart(@RequestHeader("X-auth-role") String role, @RequestHeader("X-auth-user-id") Long userId) throws InterruptedException, ExecutionException {
		Cart cart = cartService.getCart(userId).get();
		if (cart != null)
			return new ResponseEntity<>(cart.getItems(), HttpStatus.OK);
		//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		throw new CartNotFoundException("Cart not found");
	}


	/**
	 * Endpoint which adds the wanted item(s) to
	 * the cart
	 *
	 * @param productId the id of the wanted product to add
	 * @param quantity  the total number of the products
	 *                  wanted to add to the cart
	 * @param userId    the id of the cart to add the items
	 * @return a new list with the added products
	 */
	@PostMapping(value = "/cart", params = {"productId", "quantity"})
	public ResponseEntity<Cart> addItemToCart(@RequestParam Long productId,
	                                          @RequestParam Integer quantity,
	                                          @RequestHeader("X-auth-user-id") Long userId) throws InterruptedException, ExecutionException {

		Cart cart = cartService.addItemToCart(userId, productId, quantity).get();
		if (cart != null)
			return new ResponseEntity<>(cart, HttpStatus.CREATED);
		throw new UnableToAddItemException("Unable to add item to cart");
	}

	/**
	 * Endpoint which delete/remove a specific item from
	 * the list of the cart
	 *
	 * @param productId the id of the wanted to delete product
	 * @param userId    the id of the cart will remove the product
	 * @return a new list without the removed products
	 */
	@DeleteMapping(value = "/cart", params = "productId")
	public ResponseEntity<List<Item>> removeItemFromCart(@RequestParam Long productId, @RequestHeader(value = "X-auth-user-id") Long userId) throws InterruptedException, ExecutionException {
		Cart cart = cartService.deleteItemFromCart(userId, productId).get();
		if (cart != null)
			return new ResponseEntity<>(cart.getItems(), HttpStatus.NO_CONTENT);
		throw new UnableToRemoveItemException("Unable to remove item from cart");
	}
	
	@DeleteMapping("/cart")
	public ResponseEntity<Cart> removeAllItemsFromCart(@RequestHeader("X-auth-user-id") Long userId) throws InterruptedException, ExecutionException {
		Cart cart = cartService.deleteAllItemsFromCart(userId).get();
		if (cart != null)
			return new ResponseEntity<>(cart, HttpStatus.NO_CONTENT);
		throw new UnableToRemoveItemException("Unable to remove all items from cart");
	}
}
