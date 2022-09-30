package com.campusretail.userservice.controller;

import com.campusretail.userservice.dto.CredentialsDto;
import com.campusretail.userservice.dto.ReadUserDto;
import com.campusretail.userservice.dto.UserLoginDto;
import com.campusretail.userservice.dto.WriteUserDto;
import com.campusretail.userservice.exception.UserNotFoundException;
import com.campusretail.userservice.service.UserService;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * Controller that registers into the database the wanted user
 */
@RestController
@EnableAsync
public class UserAuthController {

	private final UserService userService;

	@Autowired
	public UserAuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/validateToken")
	public ResponseEntity<UserLoginDto> signIn(@RequestParam String token) throws ExecutionException, InterruptedException {
		UserLoginDto userLoginDto = userService.validateToken(token).get();
		if (userLoginDto != null)
			return new ResponseEntity<>(userLoginDto, HttpStatus.OK);

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginDto> loginUser(@RequestBody CredentialsDto credentialsDto) throws ExecutionException, InterruptedException {
		UserLoginDto userLoginDto = userService.signIn(credentialsDto).get();
		if (userLoginDto != null)
			return new ResponseEntity<>(userLoginDto, HttpStatus.OK);

		throw new UserNotFoundException("Login failed");
	}
	
	@PostMapping("/guest")
	public ResponseEntity<UserLoginDto> loginUser() throws ExecutionException, InterruptedException {
		UserLoginDto userLoginDto = userService.newGuest().get();
		if (userLoginDto != null)
			return new ResponseEntity<>(userLoginDto, HttpStatus.OK);

		throw new UserNotFoundException("Login failed");
	}

	/**
	 * Endpoint to add users into the database
	 *
	 * @param writeUserDto the user to create as DTO
	 * @return HTTP created response, bad request or internal server error in case of an error occurs
	 */
	@PostMapping(value = "/registration")
	public ResponseEntity<ReadUserDto> addUser(@RequestBody WriteUserDto writeUserDto) throws ExecutionException, InterruptedException {
		ReadUserDto readUserDto = userService.saveUserAsync(writeUserDto).get();
		if (readUserDto != null)
			return new ResponseEntity<>(readUserDto, HttpStatus.CREATED);
		throw new RuntimeException("User already registered");
	}

}
