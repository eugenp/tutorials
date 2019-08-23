package baeldung.ledger.domain;


public class Debit extends Entry {

	public Debit( final long amount, final String account, final String comment ) {

		super( amount, account, comment );
	}

	public Credit credit( final String account ) {

		return new Credit( this.getAmount(), account, this.getComment() );
	}
}
