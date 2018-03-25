package com.baeldung.spring.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Product {

	@Value("product1")
	private String productName;

	@Value("Pro123")
	private String productId;

	@Value("100")
	private String productCost;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCost() {
		return productCost;
	}

	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}

	@Override
	public String toString() {
		return "Product [productName=" + productName + ", productId=" + productId + ", productCost=" + productCost
				+ "]";
	}

}
