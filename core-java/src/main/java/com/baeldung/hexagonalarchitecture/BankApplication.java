package com.baeldung.hexagonalarchitecture;

public class BankApplication {
	public static void main (String args[]) {
		CardVerifier cardVerifier = new CardVerificationAdapter();
		
		AccountOperator accountOperator = new AccountOperationsAdapter();
		
		CreditCardPayer bankService = new BankService(cardVerifier, accountOperator);
		
		CardPaymentAdapater cardPayment = new CardPaymentAdapater(bankService);
		cardPayment.makeCardPayment(9d, "McDonald", "1234xxxxxxx3456");
	}
}
