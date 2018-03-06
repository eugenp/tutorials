package com.oreilly.jebp.ejb.businessdelegate;

import java.util.*;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Business Delegates.
 *
 * Client example for the business delegate.
**/
public class ExampleClient {
	public static void main (String[] args) throws Exception {
		// create the delegate object
		UserQueryDelegate query = new UserQueryDelegate();

		// create the query
		query.create();

		// print out first ten users
		LinkedList firstTen = query.findUsers (1,10);
		Iterator i = firstTen.iterator();
		while (i.hasNext()) {
			System.out.println (i.next());
		}

		// we're done with the query object
		query.remove();
	} // main
} // ExampleClient