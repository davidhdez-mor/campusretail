package com.campusretail.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class GlobalAuthFilter extends AbstractGatewayFilterFactory<GlobalAuthFilter.Config> {

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			exchange.getRequest().getHeaders().add("Authorization", "jwt");
			return chain.filter(exchange);
		});
	}
	

	public static class Config {
		
	}
}
