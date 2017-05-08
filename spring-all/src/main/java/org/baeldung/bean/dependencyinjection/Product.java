package org.baeldung.bean.dependencyinjection;

public class Product {
    
    private int productId;
    private String productDesc;
    
    public Product(){
        this.productId = 100;
        this.productDesc = "OCP Study Guide";
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getProductDesc() {
        return productDesc;
    }
    
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

}
