package org.nganga.bank.account.domain;

import java.io.Serializable;

public class Customer implements Serializable {
	private static final long serialVersionUID = 2L;
	private String identity;
	private String email;
	private String firstName;
	private String surname;
	private Account account;

	private Customer(String identity, String email, String firstName, String surname, Account account) {
		this.firstName = firstName;
		this.identity = identity;
		this.email = email;
		this.surname = surname;
		this.account = account;
		account.setCustomerId(identity);
	}

	public static Builder builder() {
		return new Builder();
	}

	public Account getAccount() {
		return account;
	}

	public String identity() {
		return identity;
	}

	public static class Builder {

		String identity;
		String email;
		String firstName;
		String surname;
		Account account;

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder surnameName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder identity(String identity) {
			this.identity = identity;
			return this;
		}

		public Builder account(Account account) {
			this.account = account;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Customer build() {
			return new Customer(identity, email, firstName, surname, account);
		}
	}

}
