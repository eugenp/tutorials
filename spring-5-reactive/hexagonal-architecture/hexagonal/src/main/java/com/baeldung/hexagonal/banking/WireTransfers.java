package com.baeldung.hexagonal.banking;
/**
 * 
 * Interface to bank accounts.
 *
 */
public interface WireTransfers {
	 /**
	   * Set amount of funds for bank account
	   */
	  void setFunds(String bankAccount, int amount);

	  /**
	   * Get amount of funds for bank account
	   */
	  int getFunds(String bankAccount);

	  /**
	   * Transfer funds from one bank account to another
	   */
	  boolean transferFunds(int amount, String sourceBackAccount, String destinationBankAccount);
}
