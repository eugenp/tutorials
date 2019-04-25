/**
 * 
 */
package com.pratik.userapp;

import java.util.logging.Logger;

/**
 * @author Pratik Das
 *
 */
public class OracleUserRepository implements UserRepository{

	private Logger log = Logger.getLogger(getClass().getName());
	
	public Integer addUser(User user) {
		log.info("Adding User in DB");
		return 1;
	}

}
