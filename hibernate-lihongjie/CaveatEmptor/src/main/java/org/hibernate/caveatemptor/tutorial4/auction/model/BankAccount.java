package org.hibernate.caveatemptor.tutorial4.auction.model;

/**
 * This billing strategy uses a simple bank account.
 *
 * @author Christian Bauer
 */
public class BankAccount extends BillingDetails {

	private String account;
	private String bankname;
	private String swift;

	/**
	 * No-arg constructor for JavaBean tools
	 */
	public BankAccount() { super(); }

	/**
	 * Full constructor.
	 *
	 * @param owner
	 * @param user
	 * @param account
	 * @param bankname
	 * @param swift
	 */
	public BankAccount(String owner, User user, String account, String bankname, String swift) {
		super(owner, user);
		this.account = account;
		this.bankname = bankname;
		this.swift = swift;
	}

	// ********************** Accessor Methods ********************** //

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public String getBankname() { return bankname; }
    public void setBankname(String bankname) { this.bankname = bankname; }

    public String getSwift() { return swift; }
	public void setSwift(String swift) { this.swift = swift; }

	// ********************** Common Methods ********************** //

	public String toString() {
		return  "BankAccount ('" + getId() + "'), " +
				"Account: '" + getAccount() + "'";
	}

	// ********************** Business Methods ********************** //

	public boolean isValid() {
		// TODO: Validate bank account syntax.
		return true;
	}

}
