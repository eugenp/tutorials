package baeldung.ledger;


import baeldung.ledger.adaptors.EventBusAdaptor;


/**
 * <p>
 *     This is far from a production ready implementation but it does show how the event adapter can receive messages
 *     and pass them in to the domain behavior of the ledger.
 * </p>
 * <p>
 *     This is an example usage of the application:
 * </p>
 * <code>
 *     balance general
 *     0
 *     credit general 1000
 *     1000
 *     debit general 300 payroll
 *     700
 *     balance payroll
 *     300
 *     exit
 * </code>
 * <p>
 *     There are many things that I would like to make better.  Like moving the parsing out of the worker and put it
 *     in to its own object.  User a more proper thread pool worker pool setup.  And of coarse others.
 * </p>
 */
public class Application {

	public static void main( final String ... arguments ) {

		new EventBusAdaptor().connect();
	}
}
