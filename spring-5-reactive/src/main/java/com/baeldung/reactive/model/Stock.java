package com.baeldung.reactive.model;

import java.math.BigDecimal;

public class Stock {

	private String code;
	
	private BigDecimal price;
	
	public Stock(String code, BigDecimal price) {
		this.code = code;
		this.price = price;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
