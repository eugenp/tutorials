/**
 * 
 */
package com.baeldung.hexagon.factory;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexagon.domain.Product;

public class AdapterFactory {
    
    static List<Product> list = new ArrayList<>();

    public static List<Product> getProducts() {
        return list;
    }

}
