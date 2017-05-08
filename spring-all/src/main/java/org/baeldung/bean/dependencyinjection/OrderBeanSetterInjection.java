package org.baeldung.bean.dependencyinjection;

public class OrderBeanSetterInjection {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
