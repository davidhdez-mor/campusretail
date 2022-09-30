package com.campusretail.userservice.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.campusretail.userservice.dto.CredentialsDto;
import com.campusretail.userservice.dto.ReadUserDto;
import com.campusretail.userservice.dto.UserLoginDto;
import com.campusretail.userservice.dto.WriteUserDto;
import com.campusretail.userservice.entity.User;

/**
 * Interface to define the 
 * methods for posterior use 
 * in the controllers
 */

public interface UserService {
    
    CompletableFuture<List<User>> getAllUsersAsync();
    CompletableFuture<Optional<User>> getUserByIdAsync(Long id);
    CompletableFuture<Optional<User>> getUserByNameAsync(String userName);
    CompletableFuture<ReadUserDto> saveUserAsync(WriteUserDto user);
    CompletableFuture<UserLoginDto> validateToken(String token);
    CompletableFuture<UserLoginDto> signIn(CredentialsDto credentialsDto);
    CompletableFuture<UserLoginDto> newGuest();
}
