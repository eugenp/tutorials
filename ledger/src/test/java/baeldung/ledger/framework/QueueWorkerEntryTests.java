package baeldung.ledger.framework;


import baeldung.ledger.domain.Ledger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName( "Ensure that entries are parsed and entered correctly." )
class QueueWorkerEntryTests {

	private Thread thread;
	private QueueWorker worker;

	@BeforeEach
	void before() {

		this.worker = new QueueWorker( new Ledger() );

		this.thread = new Thread( this.worker );
		this.thread.start();
	}

	@AfterEach
	void after() {

		this.thread.stop();
	}

	@Test
	@DisplayName( "Ensure that a simple debit is reflected in the resulting balance." )
	void enterSimpleDebit() throws
							ExecutionException,
							InterruptedException {

		assertEquals( -100L, (long) this.execute( "debit 100 acct test" ).get() );
	}

	@Test
	@DisplayName( "Ensure that a simple credit is reflected in the resulting balance." )
	void enterSimpleCredit() throws
							 InterruptedException,
							 ExecutionException {

		assertEquals( 100L, (long) this.execute( "credit 100 acct test" ).get() );
	}

	@Test
	@DisplayName( "Ensure that a complex debit is reflected in the resulting balance." )
	void enterComplexDebit() throws
							 InterruptedException,
							 ExecutionException {

		assertEquals( -100L, (long) this.execute( "debit 100 acct test other" ).get() );
		assertEquals( 100L, (long) this.execute( "balance other" ).get() );
	}

	@Test
	@DisplayName( "Ensure that a complex credit is reflected in the resulting balance." )
	void enterComplexCredit() throws
							  InterruptedException,
							  ExecutionException {

		assertEquals( 100L, (long) this.execute( "credit 100 other test acct" ).get() );
		assertEquals( -100L, (long) this.execute( "balance acct" ).get() );
	}

	@Test
	@DisplayName( "Ensure that after multiple entries are applied that the balance is 100." )
	void multipleEntries() throws
						   InterruptedException,
						   ExecutionException {

		assertEquals( -100L, (long) this.execute( "debit 100 acct test other" ).get() );
		assertEquals( 100L, (long) this.execute( "balance other" ).get() );

		assertEquals( -50L, (long) this.execute( "credit 50 acct test" ).get() );
		assertEquals( 50L, (long) this.execute( "debit 50 other test" ).get() );

		assertEquals( -250L, (long) this.execute( "debit 200 acct test other" ).get() );
		assertEquals( 250L, (long) this.execute( "balance other" ).get() );

	}

	private Future<Long> execute( final String ... commands ) throws
															  InterruptedException {

		//	formatter off
		Optional<Future<Long>> optional = Arrays.stream( commands )
												.map( command -> new Event<Long>( command ) )
												.map( event -> this.worker.queue( event  ) )
												.map( event -> event.future )
												.reduce( ( current, last ) -> last = current );
		// formatter on

		Future<Long> future = null;
		if( !optional.isEmpty() ) {

			future = optional.get();
			while( !future.isDone() ) {

				Thread.sleep( 500 );
			}
		}
		else {

			throw new RuntimeException( "Optional should never by empty." );
		}

		return future;
	}
}
