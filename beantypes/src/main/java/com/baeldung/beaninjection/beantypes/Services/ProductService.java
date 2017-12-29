package com.baeldung.beaninjection.beantypes.Services;

import com.baeldung.beaninjection.beantypes.Interfaces.IProductService;
import com.baeldung.beaninjection.beantypes.models.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductService implements IProductService {
    @Override
    public List<Product> getProducts() {
        List<Product> lst = new ArrayList<>();
        lst.add(new Product(1, "IPhone"));
        lst.add(new Product(1, "LG"));
        return lst;
    }
}
