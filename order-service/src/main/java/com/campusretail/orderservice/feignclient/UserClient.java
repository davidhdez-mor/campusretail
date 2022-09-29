package com.campusretail.orderservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.campusretail.orderservice.domain.User;

/**
 * Configuration class for the feign
 * product service for the API
 */
@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping(value = "/users/{id}")
    User getUserById(@PathVariable Long id);
}
