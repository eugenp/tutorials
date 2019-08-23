package baeldung.ledger.domain;


public class Credit extends Entry {

	public Credit( final long amount, final String account, final String comment ) {

		super( amount, account, comment );
	}

	public Debit debit( final String account ) {

		return new Debit( this.getAmount(), account, this.getComment() );
	}
}
