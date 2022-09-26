package com.campusretail.userservice.controller;

import com.campusretail.userservice.dto.WriteUserDto;
import com.campusretail.userservice.entity.User;
import com.campusretail.userservice.entity.UserDetails;
import com.campusretail.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that registers into the database the wanted user
 */
@RestController
public class RegisterController {

	private final UserService userService;

	private final ModelMapper mapper;

	@Autowired
	public RegisterController(UserService userService, ModelMapper mapper) {
		this.userService = userService;
		this.mapper = mapper;
	}

	//TODO Pendiente la implementación de POST para los detalles del user
	/*	@PostMapping("/user/details")
	* public ResponseEntity<UserDetails> createUserDetails(@RequestBody UserDetails userDetails) {
	*	return null;
	* }
	*/

	/**
	 * Endpoint to add users into the database
	 *
	 * @param createUser the user to create as DTO
	 * @return HTTP created response, bad request or internal server error in case of an error occurs
	 */
	@PostMapping(value = "/registration")
	public ResponseEntity<WriteUserDto> addUser(@RequestBody WriteUserDto createUser) {
		if (createUser != null) try {
			User user = this.mapper.map(createUser, User.class);
			userService.saveUser(user);
			return new ResponseEntity<>(createUser, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


}
