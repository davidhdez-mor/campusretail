package com.campusretail.orderservice.exception;

/**
 * Customized class for Cart class when 
 * a cart searched by id doesn't exist
 */
public class CartNotFoundException extends RuntimeException{

	public CartNotFoundException(String message){super(message
	);}
}
