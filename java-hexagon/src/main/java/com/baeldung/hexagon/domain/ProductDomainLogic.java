/**
 * 
 */
package com.baeldung.hexagon.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.hexagon.factory.AdapterFactory;

/**
 * @author murtaza8t8
 *
 */
public class ProductDomainLogic {

    public ProductSummary viewSummary(String name) {
        ProductSummary summary = new ProductSummary();
        try {
            summary.setName(name);
            summary.setTotalQuantity(getTotal(AdapterFactory.getProducts(), name));
        } catch (Exception e) {
            summary.setTotalQuantity(0);
        }
        return summary;
    }

    public int getTotal(List<Product> products, String name) {
        int totalQuantity = 0;
        List<Product> availableProduct = products.stream()
            .filter(line -> line.getName()
                .equals(name))
            .collect(Collectors.toList());
        for (Product product : availableProduct) {
            totalQuantity = totalQuantity + product.getQuantity();
        }
        return totalQuantity;
    }

}
