/**
 * 
 */
package com.pratik.userapp;

import java.util.logging.Logger;

/**
 * @author Pratik Das
 *
 */
public class AwsNotificationService implements NotificationService{
	private Logger log = Logger.getLogger(getClass().getName());
	
	public void notify(String message) {
		log.info(String.format("Sending \"%s\" from aws service ",message));
	}

}
