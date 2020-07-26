package com.baeldung.hexagonalspringboot.port;

import java.util.List;

import com.baeldung.hexagonalspringboot.domain.Product;

public interface IProductSearchService {

    List<Product> getproductsByCategory(String category);

    List<Product> getAllproducts();
}
