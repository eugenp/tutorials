/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.baeldung.dependency.types;

/**
 *
 * @author Tunde Michael 
 * @Date Jun 23, 2017, 9:01:00 AM
 * @Quote To code is human, to debug is coffee
 */
public class StoreManager1 {
    
    private ShippingManager shippingManager;

    public StoreManager1(ShippingManager shippingManager){
	     this.shippingManager = shippingManager;
    }

    // shippingManager getter and setter

}
