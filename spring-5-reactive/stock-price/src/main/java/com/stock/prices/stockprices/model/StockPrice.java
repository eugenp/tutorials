package com.stock.prices.stockprices.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stocks")
public class StockPrice {
	
	
	public StockPrice() {
		super();
	}
	
	

	public StockPrice(String id, String name, float price) {
		super();
		this.id=id;
		this.name = name;
		this.price = price;
	}



	@Id
	private String id;
	
	@NotBlank
	@Size(max = 140)
	private String name;
	
    @NotNull
	private float price;
    
    @NotNull
    private Date createdAt = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
