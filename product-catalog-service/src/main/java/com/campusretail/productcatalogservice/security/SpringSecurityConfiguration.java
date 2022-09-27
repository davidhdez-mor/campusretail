package com.campusretail.productcatalogservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class of the spring
 * security libraries
 */
@Configuration
public class SpringSecurityConfiguration {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic(Customizer.withDefaults())
				.csrf()
				.disable()
				.authorizeHttpRequests()
					.antMatchers("/admin/**").hasRole("ADMIN")
					.antMatchers("/**").permitAll()
				.and()
				.formLogin();
		return http.build();
	}
}
