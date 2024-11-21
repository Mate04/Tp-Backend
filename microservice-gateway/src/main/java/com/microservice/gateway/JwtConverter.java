package com.microservice.gateway;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        return Mono.fromSupplier(() -> {
            Collection<GrantedAuthority> authorities = extractRealmRoles(jwt);
            String username = jwt.getClaimAsString("preferred_username");
            return new JwtAuthenticationToken(jwt, authorities, username);
        });
    }

    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        try {
            // Extraer roles del realm_access
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

            if (realmAccess == null || !realmAccess.containsKey("roles")) {
                return Collections.emptyList();
            }

            Collection<String> roles = (Collection<String>) realmAccess.get("roles");

            return roles.stream()
                    .filter(role -> !role.startsWith("default-roles-")) // Filtrar roles por defecto
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            System.err.println("Error extracting roles from JWT: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}