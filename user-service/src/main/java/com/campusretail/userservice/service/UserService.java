package com.campusretail.userservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.campusretail.userservice.entity.User;

/**
 * Interface to define the 
 * methods for posterior use 
 * in the controllers
 */

public interface UserService {
    
    CompletableFuture<List<User>> getAllUsersAsync();
    CompletableFuture<User> getUserByIdAsync(Long id);
    CompletableFuture<User> getUserByNameAsync(String userName);
    CompletableFuture<User> saveUserAsync(User user);
}
