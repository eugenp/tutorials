package com.oreilly.jebp.ejb.lazyloading;

import javax.ejb.*;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using business interfaces.
 *
 * Local home interface of the example entity bean.
**/
public interface ForumMessageLocalHome extends EJBLocalHome {
	public ForumMessageLocal create (String title, String author,
	                          StringBuffer message) throws CreateException;

	public ForumMessageLocal findByPrimaryKey (Integer id) throws FinderException;
} // ForumMessageLocalHome