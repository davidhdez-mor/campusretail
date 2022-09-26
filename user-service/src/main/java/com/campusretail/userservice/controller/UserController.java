package com.campusretail.userservice.controller;

import com.campusretail.userservice.entity.User;
import com.campusretail.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller which manage all the user actions into the database
 */

@RestController
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
	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		if (!users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Return a specific user from the database using the name of the user as parameter
	 *
	 * @param userName string of the name of the user
	 * @return a User or an HTTP not found status
	 */
	@GetMapping(value = "/users", params = "name")
	public ResponseEntity<User> getUserByName(@RequestParam("name") String userName) {
		User user = userService.getUserByName(userName);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Return a specific user from the database using the id of the user as parameter
	 *
	 * @param id requested int id of the user
	 * @return a User or an HTTP not found status
	 */
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		User user = userService.getUserById(id);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
