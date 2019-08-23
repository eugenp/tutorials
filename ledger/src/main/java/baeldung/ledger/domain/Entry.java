package baeldung.ledger.domain;


public abstract class Entry {

	private final long amount;
	private final String account;
	private final String comment;

	public Entry( final long amount, final String account, final String comment ) {

		this.amount = amount;
		this.account = account;
		this.comment = comment;
	}

	public long getAmount() {

		return this.amount;
	}

	public String getAccount() {

		return this.account;
	}

	public String getComment() {

		return this.comment;
	}
}
