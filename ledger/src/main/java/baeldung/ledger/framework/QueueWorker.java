package baeldung.ledger.framework;


import baeldung.ledger.domain.Credit;
import baeldung.ledger.domain.Debit;
import baeldung.ledger.domain.Ledger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class QueueWorker implements Runnable {

	private final Ledger ledger;
	private final BlockingQueue<Event<Long>> queue;

	public QueueWorker( final Ledger ledger ) {

		this.ledger = ledger;
		this.queue = new LinkedBlockingQueue<>();
	}

	@Override
	public void run() {

		boolean exit = false;

		while( !exit ) {

			Event<Long> event = null;
			try {
				event = this.queue.poll();
				if( event == null ) {

					Thread.sleep( 500  );
				}
				else {

					String line = event.message;
					if( "exit".equalsIgnoreCase( line ) ) {

						exit = true;
					}
					else {

						this.execute( event, event.message.split( "\\s" ) );
					}
				}
			}
			catch( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}

	public Event<Long> queue( Event<Long> event ) {

		this.queue.add( event );

		return event;
	}

	private void execute( final Event<Long> event, final String[] details ) {

		switch( details[ 0 ].toLowerCase() ) {

			case "debit":
				this.applyDebit( details );
				this.determineBalance( event, details[ 2 ] );

				break;

			case "credit":
				this.applyCredit( details );
				this.determineBalance( event, details[ 2 ] );

				break;

			case "balance":
				this.determineBalance( event, details[ 1 ] );

				break;

			default :
				System.out.printf( "ERROR unrecognized command : '%s'\n", details[ 0 ] );

				( (Event.Result<Long>) event.future ).value( 0L );
		}
	}

	private void applyDebit( final String[] details ) {

		Debit debit = new Debit( Long.parseLong( details[ 1 ] ), details[ 2 ], details[ 3 ] );

		ledger.enter( debit );
		if( details.length == 5 ) {

			ledger.enter( debit.credit( details[ 4 ] ) );
		}
	}

	private void applyCredit( final String[] details ) {

		Credit credit = new Credit( Long.parseLong( details[ 1 ] ), details[ 2 ], details[ 3 ] );

		ledger.enter( credit );
		if( details.length == 5 ) {

			ledger.enter( credit.debit( details[ 4 ] ) );
		}
	}

	private void determineBalance( final Event<Long> event, final String account ) {

		event.done( this.ledger.balance( account ) );
	}
}
