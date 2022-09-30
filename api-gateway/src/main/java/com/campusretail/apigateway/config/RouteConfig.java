package com.campusretail.apigateway.config;

import com.campusretail.apigateway.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder, AuthenticationFilter authFilter) {
		return builder.routes()
				.route(fn ->
						fn.path("/products")
								.uri("lb://product-catalog-service/products"))
				.route(fn ->
						fn.path("/products/**")
								.filters(f ->
										f.rewritePath("/products/(?<segment>.*)", "/products/${segment}"))
								.uri("lb://product-catalog-service/products/"))
				.route(fn ->
						fn.path("/admin/**")
								.filters(
										f -> f.filter(authFilter.apply(
												new AuthenticationFilter.Config())
										))
								.uri("lb://product-catalog-service/admin/"))
				.route(fn ->
						fn.path("/cart")
								.filters(
										f -> f.filter(authFilter.apply(
												new AuthenticationFilter.Config())
										))
								.uri("lb://order-service/cart"))
				.route(fn ->
						fn.path("/order")
								.filters(
										f -> f.filter(authFilter.apply(
												new AuthenticationFilter.Config())
										))
								.uri("lb://order-service/order"))
				.route(fn ->
						fn.path("/order/**")
								.filters(
										f -> f.filter(authFilter.apply(
														new AuthenticationFilter.Config())
												)
												.rewritePath("/order/(?<segment>.*)", "/order/${segment}"))
								.uri("lb://order-service/order"))
				.route(fn ->
						fn.path("/login")
								.uri("lb://user-service/login"))
				.route(fn ->
						fn.path("/registration")
								.uri("lb://user-service/registration"))
				.route(fn ->
						fn.path("/users/**")
								.filters(
										f -> f.filter(authFilter.apply(
														new AuthenticationFilter.Config())
												)
												.rewritePath("/users/(?<segment>.*)", "/users/${segment}"))
								.uri("lb://user-service/users/"))
				.build();
	}
}
