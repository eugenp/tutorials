package org.baeldung.bean.dependencyinjection;

public class OrderBeanConstructorInjection {
    
    private Product product;
    
    public OrderBeanConstructorInjection(Product product){
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    

}
