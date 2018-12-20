package com.baeldung.hexagonalarchitecture;

public interface CreditCardPayer {
	public int chargeCard (Double amount, String payer, String cardDetails);
}

