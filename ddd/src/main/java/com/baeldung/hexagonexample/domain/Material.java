package com.baeldung.hexagonexample.domain;

import java.math.BigDecimal;

public class Material {

	private String id;
	private String materialName;
	private BigDecimal price;

	// setter and getter methods

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	// to string method

	@Override
	public String toString() {
		return "Material [id=" + id + ", materialName=" + materialName + ", price=" + price + "]";
	}

}