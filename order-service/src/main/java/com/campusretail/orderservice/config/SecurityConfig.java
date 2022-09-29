package com.campusretail.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class of the spring
 * security libraries
 */
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic(Customizer.withDefaults())
				.csrf()
				.disable()
				.authorizeHttpRequests()
					.antMatchers("/**").permitAll();
		return http.build();
	}
}
