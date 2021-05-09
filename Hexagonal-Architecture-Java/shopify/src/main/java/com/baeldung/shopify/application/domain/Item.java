package com.baeldung.shopify.application.domain;

import lombok.Data;

@Data
public class Item {

	private String productId;
	private String name;
	private int price;

}
