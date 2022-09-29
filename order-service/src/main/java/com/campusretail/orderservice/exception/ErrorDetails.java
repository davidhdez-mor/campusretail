package com.campusretail.orderservice.exception;

import java.time.LocalDate;

/**
 * Customized class to show
 * more information about the
 * errors
 */
public class ErrorDetails {

	private LocalDate timeStamp;
	private String message;
	private String details;

	public ErrorDetails(LocalDate timeStamp, String message, String details) {
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}

	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}
