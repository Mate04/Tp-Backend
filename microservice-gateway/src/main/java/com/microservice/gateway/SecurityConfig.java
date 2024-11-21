package com.microservice.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@RequiredArgsConstructor
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    private final JwtConverter jwtConverter;
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                // Deshabilitar CSRF para APIs stateless
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                // Configurar autorización de solicitudes
                .authorizeExchange(exchanges -> exchanges
                        // Ruta de vehículos solo para usuarios con rol ADMIN
                        .pathMatchers(HttpMethod.POST,"/api/prueba").hasRole("EMPLEADO")
                        .pathMatchers(HttpMethod.PATCH,"/api/prueba/finalizar/**").hasAnyRole("EMPLEADO", "ADMIN")
                        .pathMatchers("api/prueba/**").hasRole("ADMIN")
                        .pathMatchers("api/posicion").hasRole("VEHICULO")
                        .pathMatchers("api/vehiculo/**").hasRole("ADMIN")
                        .pathMatchers("api/notificacion/advertencia/**").hasRole("ADMIN")
                        .pathMatchers("api/notificacion/promocion/**").hasRole("EMPLEADO")


                        // Otras rutas pueden tener diferentes configuraciones de acceso
                        .anyExchange().authenticated()
                )

                // Configurar OAuth2 Resource Server con JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtConverter)
                        )
                );

        return http.build();
    }
}