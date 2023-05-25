/**
 * 
 */
package com.baeldung.springcloudgateway.custompredicates.factories;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import javax.validation.constraints.NotEmpty;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.HttpCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import com.baeldung.springcloudgateway.custompredicates.service.GoldenCustomerService;

/**
 * @author Philippe
 *
 */
public class GoldenCustomerRoutePredicateFactory extends AbstractRoutePredicateFactory<GoldenCustomerRoutePredicateFactory.Config> {

    private final GoldenCustomerService goldenCustomerService;
    
    public GoldenCustomerRoutePredicateFactory(GoldenCustomerService goldenCustomerService ) {
        super(Config.class);
        this.goldenCustomerService = goldenCustomerService;
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("isGolden","customerIdCookie");
    }


    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        
        return (ServerWebExchange t) -> {
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
        };
    }
    
    
    @Validated
    public static class Config {        
        boolean isGolden = true;
        
        @NotEmpty
        String customerIdCookie = "customerId";
        
        
        public Config() {}
        
        public Config( boolean isGolden, String customerIdCookie) {
            this.isGolden = isGolden;
            this.customerIdCookie = customerIdCookie;
        }
        
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
