package com.baeldung.evaluation.di.example;

public class Details {
	String paymentReason;

	public Details() {
		super();
	}

	public Details(String paymentReason) {
		this.paymentReason = paymentReason;
	}

	public String getPaymentReason() {
		return paymentReason;
	}

	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}

	@Override
	public String toString() {
		return "Payment Reason: " + this.paymentReason;
	}

}
