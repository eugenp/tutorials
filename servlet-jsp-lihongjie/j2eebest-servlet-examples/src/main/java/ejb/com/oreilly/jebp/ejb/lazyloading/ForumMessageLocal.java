package com.oreilly.jebp.ejb.lazyloading;

import javax.ejb.EJBLocalObject;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using business interfaces.
 *
 * Local interface of the example entity bean.
**/
public interface ForumMessageLocal extends EJBLocalObject {
	public StringBuffer getMessageText();
	public void setMessageText (StringBuffer text);
} // ForumMessageLocal