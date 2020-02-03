package com.baeldung.hexagonal.bridge;

import com.baeldung.hexagonal.domain.ProductVO;

public class ProductResponse {
    private ProductVO productVO;

    public ProductResponse(ProductVO productVO) {
        this.productVO = productVO;
    }

    //getters and setters...
    public ProductVO getProduct() {
        return productVO;
    }

    public void setProduct(ProductVO productVO) {
        this.productVO = productVO;
    }
}
