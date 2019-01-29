package com.example.orth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CorporateSales {
	private @Id @GeneratedValue Long id;
	private String item;
	private String region;
	private double amount;

	public CorporateSales(String item, String region, double amount) {
		this.item = item;
		this.region = region;
		this.amount = amount;
	}

	public String getItem() {
		return item;
	}

	public String getRegion() {
		return region;
	}

	public double getAmount() {
		return amount;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setAmount(double amount) { // TODO Auto-generated method stub
		this.amount = amount;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}