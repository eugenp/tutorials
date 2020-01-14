/**
 * 
 */
package com.baeldung.springcloudgateway.custompredicates.factories;

import java.security.Principal;
import java.util.List;
import java.util.function.Predicate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.http.HttpCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import com.baeldung.springcloudgateway.custompredicates.service.GoldenCustomerService;

/**
 * @author Philippe
 *
 */
public class GoldenCustomerPredicateFactory extends AbstractRoutePredicateFactory<GoldenCustomerPredicateFactory.Config> {

    private final GoldenCustomerService goldenCustomerService;
    
    public GoldenCustomerPredicateFactory(GoldenCustomerService goldenCustomerService ) {
        super(Config.class);
        this.goldenCustomerService = goldenCustomerService;
    }

    

    @Override
    public String name() {
        return "GoldenCustomer";
    }



    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            
            @Override
            public boolean test(ServerWebExchange t) {                
                List<HttpCookie> cookies = t.getRequest()
                  .getCookies()
                  .get(config.getCustomerIdCookie());
                
                boolean isGolden; 
                if ( cookies == null || cookies.isEmpty()) {
                    isGolden = false;
                }
                else {                
                    String customerId = cookies.get(0).getValue();                
                    isGolden = goldenCustomerService.isGoldenCustomer(customerId);
                }
                
                return config.isGolden()?isGolden:!isGolden;
            }
        };
    }
    
    
    @Validated
    public static class Config {
        
        boolean isGolden = true;
        
        @NotEmpty
        String customerIdCookie = "customerId";
        
        public boolean isGolden() {
            return isGolden;
        }
        
        public void setGolden(boolean value) {
            this.isGolden = value;
        }

        /**
         * @return the customerIdCookie
         */
        public String getCustomerIdCookie() {
            return customerIdCookie;
        }

        /**
         * @param customerIdCookie the customerIdCookie to set
         */
        public void setCustomerIdCookie(String customerIdCookie) {
            this.customerIdCookie = customerIdCookie;
        }
        
        
        
    }
    
}
