package com.hexa.example.domain;

public class FoodOrderSummary {

	private String orderStatus;
	
	private String billAmount;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Total Bill Value "+this.billAmount+" and order status is "+this.orderStatus;
	}
	
	
}
