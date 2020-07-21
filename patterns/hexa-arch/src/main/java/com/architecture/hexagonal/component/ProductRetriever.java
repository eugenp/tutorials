package com.architecture.hexagonal.component;


import com.architecture.hexagonal.model.Product;

public interface ProductRetriever {

    /**
     * retrieves a product by their id
     * @param id
     * @return a Product object
     */
    Product retrieveById(int id);
}
