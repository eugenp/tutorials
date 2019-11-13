package com.concertosoft.VO;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class PayerVO {
	
	String name="Deepa";
	int code=10;
	String address;
	double amount;
	String currency;

	 public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String curr) {
		this.currency = curr;
	}
	
public String toString() {
	return name+","+address+","+code+", "+amount+","+currency;
}


}
