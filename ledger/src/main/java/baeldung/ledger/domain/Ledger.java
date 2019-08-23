package baeldung.ledger.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Ledger {

	private List<Entry> entries = new ArrayList<>();

	public long enter( final Entry entry ) {

		this.entries.add( entry );

		return balance( entry.getAccount() );
	}

	public long balance( final String account ) {

		return credits( account ) - debits( account );
	}

	private long credits( final String account ) {

		//	format off
		Optional<Long> optional = entries.stream()
										 .filter( e -> account.equals( e.getAccount() ) )
										 .filter( e -> e instanceof Credit )
										 .map( Entry::getAmount )
										 .reduce( ( amount, total ) -> total + amount );
		//	format on

		if( optional.isEmpty() ) {

			return 0;
		}

		return optional.get();
	}

	private long debits( final String account ) {

		//	format off
		Optional<Long> optional = entries.stream()
										 .filter( e -> account.equals( e.getAccount() ) )
										 .filter( e -> e instanceof Debit )
										 .map( Entry::getAmount )
										 .reduce( ( amount, total ) -> total + amount );
		//	format on

		if( optional.isEmpty() ) {

			return 0;
		}

		return optional.get();
	}
}
