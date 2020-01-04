/**
 * 
 */
package com.baeldung.hexagon.ports;

import com.baeldung.hexagon.domain.Product;
import com.baeldung.hexagon.domain.ProductSummary;

/**
 * @author murtaza8t8
 *
 */
public interface ProductPort {

    public Product addProduct(String name, String description, int quantity);

    public ProductSummary viewSummary(String name);

}
