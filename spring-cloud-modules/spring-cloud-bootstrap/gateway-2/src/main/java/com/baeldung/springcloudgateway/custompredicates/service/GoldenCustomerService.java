/**
 * 
 */
package com.baeldung.springcloudgateway.custompredicates.service;

import org.springframework.stereotype.Component;

/**
 * @author Philippe
 *
 */
@Component
public class GoldenCustomerService {
    
    public boolean isGoldenCustomer(String customerId) {
        
        // TODO: Add some AI logic to check is this customer deserves a "golden" status ;^)
        if ( "baeldung".equalsIgnoreCase(customerId)) {
            return true;
        }
        else {
            return false;
        }
    }

}
