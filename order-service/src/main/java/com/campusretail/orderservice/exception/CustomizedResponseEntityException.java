package com.campusretail.orderservice.exception;

import com.campusretail.orderservice.domain.Cart;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

/**
 * An Exception handler class to
 * manage customizes errors and 
 * messages 
 */
public class CustomizedResponseEntityException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request)
	{
		ErrorDetails errorDetails= new ErrorDetails(LocalDate.now(),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CartNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleCartNotFoundException(Exception ex, WebRequest request)
	{
		ErrorDetails errorDetails= new ErrorDetails(LocalDate.now(),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleOrderNotFoundException(Exception ex, WebRequest request)
	{
		ErrorDetails errorDetails= new ErrorDetails(LocalDate.now(),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                              HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		ErrorDetails errorDetails= new ErrorDetails(LocalDate.now(),
				"Total errors"+ex.getErrorCount()+" 0>"+ex.getFieldError().getDefaultMessage(), request.getDescription(false));

		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
