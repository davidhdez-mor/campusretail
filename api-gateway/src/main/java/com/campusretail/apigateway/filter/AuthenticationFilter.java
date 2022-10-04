package com.campusretail.apigateway.filter;

import com.campusretail.apigateway.dto.UserDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
	private final WebClient.Builder webClientBuilder;

	public AuthenticationFilter(WebClient.Builder builder) {
		super(Config.class);
		this.webClientBuilder = builder;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				throw new RuntimeException("Missing auth information");

			String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String[] authValue = authHeader.split(" ");

			if (authValue.length != 2 & !authValue[0].equals("Bearer"))
				throw new RuntimeException("Incorrect authorization structure");

			return webClientBuilder.build()
					.post()
					.uri("lb://user-service/validateToken?token=" + authValue[1])
					.retrieve()
					.bodyToMono(UserDto.class)
					.map(userDto -> {
						exchange.getRequest()
								.mutate()
								.header("X-auth-user-id", String.valueOf(userDto.getId()))
								.header("X-auth-role", String.valueOf(userDto.getRole()));
						return exchange;
					}).flatMap(chain::filter);
		};
	}

	public static class Config {

	}

}
