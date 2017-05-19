package org.baeldung.bean.dependencyinjection;

public class OrderBeanXMLConstructorDI {

    private Product product;

    public OrderBeanXMLConstructorDI(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
