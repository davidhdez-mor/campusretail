package com.campusretail.userservice.controller;

import com.campusretail.userservice.dto.ReadUserDto;
import com.campusretail.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Controller which manage all the user actions into the database
 */

@RestController
@EnableAsync
public class UserController {

	private final ModelMapper modelMapper;

	private final UserService userService;
	
	@Autowired
	public UserController(ModelMapper modelMapper, UserService userService) {
		this.modelMapper = modelMapper;
		this.userService = userService;
	}

	/**
	 * Endpoint to get all the users in the database
	 *
	 * @return A collection of users or an HTTP not found response
	 */
	@GetMapping("/users")
	@Async("threadPoolExecutor")
	public CompletableFuture<ResponseEntity<List<ReadUserDto>>> getAllUsers() throws InterruptedException, ExecutionException{
		List<ReadUserDto> users = userService.getAllUsers()
				.get()
				.stream()
				.map(user -> this.modelMapper.map(user, ReadUserDto.class))
				.collect(Collectors.toList());
		if (!users.isEmpty()) {
			return CompletableFuture.completedFuture(new ResponseEntity<>(users, HttpStatus.OK));
		}
		return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * Return a specific user from the database using the name of the user as parameter
	 *
	 * @param userName string of the name of the user
	 * @return a User or an HTTP not found status
	 */
	@GetMapping(value = "/users", params = "name")
	@Async("threadPoolExecutor")
	public CompletableFuture<ResponseEntity<ReadUserDto>> getUserByName(@RequestParam("name") String userName) {
		ReadUserDto user = this.modelMapper.map(userService.getUserByName(userName), ReadUserDto.class);
		if (user != null) {
			return CompletableFuture.completedFuture(new ResponseEntity<>(user, HttpStatus.OK));
		}
		return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * Return a specific user from the database using the id of the user as parameter
	 *
	 * @param id requested int id of the user
	 * @return a User or an HTTP not found status
	 */
	@GetMapping("/users/{id}")
	@Async("threadPoolExecutor")
	public CompletableFuture<ResponseEntity<ReadUserDto>> getUserById(@PathVariable("id") Long id) throws InterruptedException, ExecutionException {
		ReadUserDto user = this.modelMapper.map(userService.getUserById(id), ReadUserDto.class);
		if (user != null) {
			return CompletableFuture.completedFuture(new ResponseEntity<>(user, HttpStatus.OK));
		}
		return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
