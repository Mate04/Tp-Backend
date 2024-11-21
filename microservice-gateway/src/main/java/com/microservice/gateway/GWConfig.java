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
                //Vehiculo
                //todo: solo el rol admin puede acceder aca
                .route(p -> p
                        .path("/api/vehiculo/**")
                        .uri(uriLogica)
                )
                //Posicion
                //todo: aca rol admin y usuario
                .route(p -> p.path("/api/posicion/**")
                        .uri(uriLogica)
                )
                //PRUEBA
                //todo: aca rol admin y una parte empleado
                .route(p -> p.path("/api/prueba/**")
                        .uri(uriLogica)
                )
                //inicidente de prueba
                //todo: aca rol admin y una parte empleado
                .route(p -> p.path("/api/notificacion/**")
                        .uri(uriNotif)
                )
                .build();
    }
}
