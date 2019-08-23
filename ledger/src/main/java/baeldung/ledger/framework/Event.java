package baeldung.ledger.framework;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class Event<T> {

	public final String message;
	public final Future<T> future;

	public Event( final String message ) {

		this.message = message;

		this.future = (Future<T>) new Event.Result();
	}

	public void done( T value ) {

		( (Result<T>) this.future ).value( value );
	}

	public static class Result<V> implements Future {

		private boolean done;
		private V value;

		@Override
		public boolean cancel( boolean mayInterruptIfRunning ) {

			return false;
		}

		@Override
		public boolean isCancelled() {

			return false;
		}

		@Override
		public boolean isDone() {

			return this.done;
		}

		@Override
		public V get() throws
					   InterruptedException,
					   ExecutionException {

			return this.value;
		}

		@Override
		public V get( long timeout, TimeUnit unit ) throws
													InterruptedException,
													ExecutionException,
													TimeoutException {
			return this.value;
		}

		public void value( V value ) {

			this.done = true;
			this.value = value;
		}
	}
}
