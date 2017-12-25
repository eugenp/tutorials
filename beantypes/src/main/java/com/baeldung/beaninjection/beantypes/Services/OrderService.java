package com.baeldung.beaninjection.beantypes.Services;

import com.baeldung.beaninjection.beantypes.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private ProductService productService;

    @Autowired
    @Qualifier("vipCustomerService")
    private CustomerService customerService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public Customer getOrderRelatedCustomer() {
        return this.customerService.GetCustomer(1);
    }
}
