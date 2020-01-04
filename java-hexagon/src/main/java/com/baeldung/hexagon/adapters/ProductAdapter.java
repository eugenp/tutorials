/**
 * 
 */
package com.baeldung.hexagon.adapters;

import com.baeldung.hexagon.domain.Product;
import com.baeldung.hexagon.domain.ProductDomainLogic;
import com.baeldung.hexagon.domain.ProductSummary;
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
        ProductDomainLogic prodDomainLogic = new ProductDomainLogic();
        return prodDomainLogic.viewSummary(name);
    }

}
