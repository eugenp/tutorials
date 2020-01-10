/**
 * 
 */
package com.baeldung.hexagon.adapters;

import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.hexagon.domain.Product;
import com.baeldung.hexagon.domain.ProductSummary;
import com.baeldung.hexagon.factory.AdapterFactory;
import com.baeldung.hexagon.ports.ProductPort;

public class ProductAdapter implements ProductPort {

    @Override
    public Product addProduct(String name, String description, int quantity) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setQuantity(quantity);
        return product;
    }

    @Override
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

    private int getTotal(List<Product> products, String name) {
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
