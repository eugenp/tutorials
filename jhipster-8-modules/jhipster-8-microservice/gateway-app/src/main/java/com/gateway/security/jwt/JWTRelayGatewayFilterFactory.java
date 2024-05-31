package com.gateway.security.jwt;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

@Component
public class JWTRelayGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final String BEARER = "Bearer ";

    private ReactiveJwtDecoder jwtDecoder;

    public JWTRelayGatewayFilterFactory(ReactiveJwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String bearerToken = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
            if (bearerToken == null) {
                // Allow anonymous requests.
                return chain.filter(exchange);
            }
            String token = this.extractToken(bearerToken);
            return jwtDecoder.decode(token).thenReturn(withBearerAuth(exchange, token)).flatMap(chain::filter);
        };
    }

    private String extractToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.length() > 7 && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        throw new IllegalArgumentException("Invalid token in Authorization header");
    }

    private ServerWebExchange withBearerAuth(ServerWebExchange exchange, String authorizeToken) {
        return exchange.mutate().request(r -> r.headers(headers -> headers.setBearerAuth(authorizeToken))).build();
    }
}
