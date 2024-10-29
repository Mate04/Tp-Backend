package com.microservice.logica.utils;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestInterceptor interceptor) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(interceptor);
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestInterceptor myInterceptor() {
        return (request, body, execution) -> {
            // Lógica del interceptor
            return execution.execute(request, body);
        };
    }
}
