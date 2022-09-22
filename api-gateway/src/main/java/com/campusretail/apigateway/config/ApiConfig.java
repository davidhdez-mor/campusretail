package com.campusretail.apigateway.config;

import com.campusretail.apigateway.filter.SessionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public SessionFilter sessionFilter(){
        return new SessionFilter();
    }
}
