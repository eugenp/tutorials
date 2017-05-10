package org.baeldung.bean.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderBeanAnnoConstructorDI {

    private Product product;

    @Autowired
    public OrderBeanAnnoConstructorDI(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }
}
