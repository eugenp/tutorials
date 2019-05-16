package com.baeldung.model;

import java.util.List;

public class OrderSummary {

	private String orderStatus;
	private List<Item> orderItems;
	private Double billAmount;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<Item> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<Item> orderItems) {
		this.orderItems = orderItems;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	@Override
	public String toString() {
		return "OrderSummary [orderStatus=" + orderStatus + ", orderItems=" + orderItems + ", billAmount=" + billAmount
				+ "]";
	}

}
