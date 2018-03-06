package com.oreilly.jebp.ejb.lookupcache;

import javax.naming.*;
import javax.rmi.PortableRemoteObject;

// example homes to cache
import com.oreilly.jebp.ejb.largequeries.UserQueryHome;
import com.oreilly.jebp.ejb.lazyloading.ForumMessageLocalHome;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Caching JNDI lookups.
 *
 * Example cache singleton object.
**/
public class EJBHomeCache {
	private static EJBHomeCache instance = null;

	protected Context ctx = null;
	protected UserQueryHome userQueryHome = null;
	protected ForumMessageLocalHome forumMessageLocalHome = null;

	private EJBHomeCache()
	{
		try {
			ctx = new InitialContext();
			userQueryHome = (UserQueryHome)PortableRemoteObject.narrow (
				ctx.lookup ("java:comp/env/MyHome"),
				UserQueryHome.class);
			forumMessageLocalHome = (ForumMessageLocalHome)PortableRemoteObject.narrow (
				ctx.lookup ("java:comp/env/ForumMessageLocalHome"),
				ForumMessageLocalHome.class);

		} catch (Exception e) {
			// handle JNDI exceptions here, and maybe throw
			// application level exception
		}
	}
	public static synchronized EJBHomeCache getInstance()
	{
		if (instance == null) instance = new EJBHomeCache();
		return instance;
	}

	public UserQueryHome getUserQueryHome()
	{
		return userQueryHome;
	}

	public ForumMessageLocalHome getForumMessageLocalHome()
	{
		return forumMessageLocalHome;
	}
} // EJBHomeCache