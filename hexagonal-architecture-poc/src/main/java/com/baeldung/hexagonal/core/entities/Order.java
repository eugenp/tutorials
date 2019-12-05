package com.baeldung.hexagonal.core.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="Orders") public class Order {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	Double total;
	
	public Order() {
		super();
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Long getId() {
		return id;
	}
	
	
}
