package com.architecture.hexagonal.component;

import com.architecture.hexagonal.model.Product;

public interface ProductService {
    /**
     * Get the product using the product ID.
     *
     * @param productId The product ID in the physical product catalogue.
     * @return The product details.
     */
     Product getProduct(int productId);

}
