package com.baeldung.hexagonalarchitecture;

public class CardPaymentAdapater{
	private CreditCardPayer paymentService;
	
	public CardPaymentAdapater(CreditCardPayer paymentService) {
		this.paymentService = paymentService;
	}
	
	public int makeCardPayment(Double amount, String payer, String cardDetails) {
		return paymentService.chargeCard(amount, payer, cardDetails);
	}
}
