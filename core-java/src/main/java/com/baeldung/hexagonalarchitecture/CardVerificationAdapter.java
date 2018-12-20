package com.baeldung.hexagonalarchitecture;

public class CardVerificationAdapter implements CardVerifier{
	@Override
	public boolean verifyCard(String cardDetails) {
		System.out.println("Card verifiation successful for card details : "+cardDetails);
		
		return true;
	}
}
