package com.campusretail.productcatalogservice.exception;

public class RequestEmptyException extends RuntimeException {
	public RequestEmptyException(String msg) {
		super(msg);
	}
}
