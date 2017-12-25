package com.baeldung.beaninjection.beantypes.Interfaces;
import com.baeldung.beaninjection.beantypes.models.Product;

import java.util.List;

public interface IProductService {

    List<Product> getProducts();
}
