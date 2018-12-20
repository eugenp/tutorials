package com.baeldung.hexagonalarchitecture;

public class BankService implements CreditCardPayer{
	
	private CardVerifier cardVerifier;
	
	private AccountOperator accountOperator;
	
	public BankService (CardVerifier cardVerifier, AccountOperator accountOperator) {
		this.cardVerifier = cardVerifier;
		this.accountOperator = accountOperator;
	}

	private String fetchAccountDetails(String cardDetails) {
		String accountDetails = "account details";
		
		return accountDetails;
	}
	
	private String buildTransactionDetails (Double amount, String payer, String cardDetails) {
		String transactionDetails = "transaction details";
		
		return transactionDetails;
	}
	
	@Override
	public int chargeCard(Double amount, String payer, String cardDetails) {
		int paymentStatus = 0;
		
		String accountDetails = fetchAccountDetails (cardDetails);
		
		if (cardVerifier.verifyCard(cardDetails)) {
			if (accountOperator.checkBalance(cardDetails) > amount) {
				String transactionDetails = buildTransactionDetails(amount, payer, cardDetails);
				paymentStatus = accountOperator.debitAccount(amount, accountDetails, transactionDetails);				
			}
		}
		
		return paymentStatus;
	}
}
