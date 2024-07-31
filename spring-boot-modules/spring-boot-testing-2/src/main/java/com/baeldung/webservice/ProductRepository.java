package com.baeldung.webservice;

import com.baeldung.webservice.generated.Product;

public interface ProductRepository {

    Product findProduct(String id);

}
