package com.baeldung.evaluation.di.example;

public class CardPayment implements IPayment {

	@Override
	public void pay(int amount) {
		System.out.println("Amount Paid:" + amount + ", Payment Type: Card");
	}

}
