package com.baeldung.beaninjection.beantypes.Services;

import com.baeldung.beaninjection.beantypes.models.Customer;
import com.baeldung.beaninjection.beantypes.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShoppingCartService {

    private ProductService productService;
    private OrderService orderService;

    @Autowired
    public ShoppingCartService(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    public Customer GetRelatedCustomer()
    {
        return orderService.getRelatedCustomer();
    }

    public List<Product> GetRelatedProduct()
    {
        return productService.getProducts();
    }
}
