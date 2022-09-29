package com.campusretail.userservice.controller;

import com.campusretail.userservice.dto.CredentialsDto;
import com.campusretail.userservice.dto.ReadUserDto;
import com.campusretail.userservice.dto.UserLoginDto;
import com.campusretail.userservice.dto.WriteUserDto;
import com.campusretail.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<UserLoginDto> signIn(@RequestParam String token)
			throws ExecutionException,
			InterruptedException {
		return ResponseEntity.ok(userService.validateToken(token).get());
	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginDto> loginUser(@RequestBody CredentialsDto credentialsDto) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(userService.signIn(credentialsDto).get());
	}

	/**
	 * Endpoint to add users into the database
	 *
	 * @param writeUserDto the user to create as DTO
	 * @return HTTP created response, bad request or internal server error in case of an error occurs
	 */
	@PostMapping(value = "/registration")
	public ResponseEntity<ReadUserDto> addUser(@RequestBody WriteUserDto writeUserDto) {
		try {
			ReadUserDto readUserDto = userService.saveUserAsync(writeUserDto).get();
			return new ResponseEntity<>(readUserDto, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
