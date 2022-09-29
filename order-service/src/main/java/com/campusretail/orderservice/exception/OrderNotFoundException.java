package com.campusretail.orderservice.exception;

/**
 * Customized class for Order class when 
 * a cart searched by id doesn't exist
 */
public class OrderNotFoundException extends RuntimeException{
	
	public OrderNotFoundException(String message){super(message);}
}
