package com.campusretail.userservice.controller;

import com.campusretail.userservice.dto.ReadUserDto;
import com.campusretail.userservice.entity.User;
import com.campusretail.userservice.exception.UserNotFoundException;
import com.campusretail.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
	 * @return A collection of users or an HTTP not found response
	 */
	@GetMapping("/users")
	public ResponseEntity<List<ReadUserDto>> getAllUsers() throws ExecutionException, InterruptedException{
		List<ReadUserDto> users = userService.getAllUsersAsync()
				.get()
				.stream()
				.map(user -> this.modelMapper.map(user, ReadUserDto.class))
				.collect(Collectors.toList());
		if (!users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		throw new UserNotFoundException("Users not found");
	}

	/**
	 * Return a specific user from the database using the name of the user as parameter
	 *
	 * @param userName string of the name of the user
	 * @return a User or an HTTP not found status
	 */
	@GetMapping(value = "/users", params = "name")
	public ResponseEntity<ReadUserDto> getUserByName(@RequestParam("name") String userName) throws ExecutionException, InterruptedException{
		Optional<User> user = userService.getUserByNameAsync(userName).get();
		if (user != null) {
			ReadUserDto readUserDto = this.modelMapper.map(user, ReadUserDto.class);
			return new ResponseEntity<>(readUserDto, HttpStatus.OK);
		}
		throw new UserNotFoundException("User with name: " + userName + " not found");
	}

	/**
	 * Return a specific user from the database using the id of the user as parameter
	 *
	 * @param id requested int id of the user
	 * @return a User or an HTTP not found status
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<ReadUserDto> getUserById(@PathVariable("id") Long id) throws ExecutionException, InterruptedException {
		User user = userService.getUserByIdAsync(id).get();
		if (user != null) {
			ReadUserDto readUserDto = this.modelMapper.map(user, ReadUserDto.class);
			return new ResponseEntity<>(readUserDto, HttpStatus.OK);
		}
		throw new UserNotFoundException("User with id: " + id + " not found");
	}
}