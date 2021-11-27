package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports.exceptions;

public class ProductNotFoundException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 4968197225679145792L;
    private String productCode;

    public ProductNotFoundException() {
        super();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
