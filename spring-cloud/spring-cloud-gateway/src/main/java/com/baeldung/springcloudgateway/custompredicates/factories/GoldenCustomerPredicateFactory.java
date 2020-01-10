/**
 * 
 */
package com.baeldung.springcloudgateway.custompredicates.factories;

import java.util.function.Predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author Philippe
 *
 */
public class GoldenCustomerPredicateFactory extends AbstractRoutePredicateFactory<GoldenCustomerPredicateFactory.Config> {

    public GoldenCustomerPredicateFactory() {
        super(Config.class);
    }


    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            
            @Override
            public boolean test(ServerWebExchange t) {
                return false;
            }
        };
    }
    
    
    @Validated
    public static class Config {
        
    }
    
}
