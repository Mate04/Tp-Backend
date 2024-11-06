package com.microservice.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {
    @Value("${api.url.microservice-logica}")
    private String uriLogica;
    @Value("${api.url.microservice-notif}")
    private String uriNotif;

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder
                                        ) {
        return builder.routes()
                .route(p -> p
                        .path("/api/vehiculo/**")
                        .uri(uriLogica)
                )
                .route(p -> p.path("/api/prueba")
                        .and()
                        .method("POST")
                        .uri(uriLogica)
                )
                .build();
    }
}
