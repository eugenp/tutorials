package com.baeldung.singleton.synchronization;

/**
 * Double-checked locking design pattern applied to a singleton.
 * 
 * @author Donato Rimenti
 *
 */
public class DclSingleton {

	/**
	 * Current instance of the singleton.
	 */
	private static volatile DclSingleton instance;

	/**
	 * Private constructor to avoid instantiation.
	 */
	private DclSingleton() {
	}

	/**
	 * Returns the current instance of the singleton.
	 * 
	 * @return the current instance of the singleton
	 */
	public static DclSingleton getInstance() {
		if (instance == null) {
			synchronized (DclSingleton.class) {
				if (instance == null) {
					instance = new DclSingleton();
				}
			}
		}
		return instance;
	}

}