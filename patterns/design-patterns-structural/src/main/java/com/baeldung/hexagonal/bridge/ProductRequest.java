package com.baeldung.hexagonal.bridge;

import com.baeldung.hexagonal.domain.ProductVO;

public class ProductRequest {
    private ProductVO product;

    public ProductRequest() {
    }

    public ProductRequest(ProductVO product) {
        this.product = product;
    }

    //getters and setters...

    public ProductVO getProduct() {
        return product;
    }

    public void setProduct(ProductVO product) {
        this.product = product;
    }
}
