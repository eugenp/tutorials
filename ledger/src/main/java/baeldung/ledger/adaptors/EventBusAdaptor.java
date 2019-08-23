package baeldung.ledger.adaptors;


import baeldung.ledger.domain.Ledger;
import baeldung.ledger.framework.Event;
import baeldung.ledger.framework.QueueWorker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;


public class EventBusAdaptor {

	public void connect() {

		QueueWorker worker = new QueueWorker( new Ledger() );
		Thread thread = new Thread( worker );

		thread.start();
		try( BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) ) ) {

			for( String line = reader.readLine() ; !line.equals( "exit" ) ; line = reader.readLine() ) {

				Event<Long> event = new Event<>( line );
				worker.queue( event );

				while( !event.future.isDone() ) {

					Thread.sleep( 500 );
				}

				System.out.println( event.future.get() );

				while( reader.ready() ) {

					Thread.sleep( 500 );
				}
			}

			thread.stop();
		}
		catch( IOException | InterruptedException | ExecutionException e ) {

			e.printStackTrace();
		}
	}
}
