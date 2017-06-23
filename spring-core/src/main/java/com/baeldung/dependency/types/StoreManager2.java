
package com.baeldung.dependency.types;

/**
 *
 * @author Tunde Michael 
 * @Date Jun 23, 2017, 11:15:44 AM
 * @Quote To code is human, to debug is coffee
 */
public class StoreManager2 {
    
    private ShippingManager shippingManager;

    public void setShippingManager(ShippingManager shippingManager){
	    this.shippingManager = shippingManager;
    }

    // shippingManager getter 

}
