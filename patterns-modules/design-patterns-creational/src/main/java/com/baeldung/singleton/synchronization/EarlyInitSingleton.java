package com.baeldung.singleton.synchronization;

/**
 * Singleton with early initialization. Inlines the singleton instance
 * initialization.
 * 
 * @author Donato Rimenti
 *
 */
public class EarlyInitSingleton {

	/**
	 * Current instance of the singleton.
	 */
	private static final EarlyInitSingleton INSTANCE = new EarlyInitSingleton();

	/**
	 * Private constructor to avoid instantiation.
	 */
	private EarlyInitSingleton() {
	}

	/**
	 * Returns the current instance of the singleton.
	 * 
	 * @return the current instance of the singleton
	 */
	public static EarlyInitSingleton getInstance() {
		return INSTANCE;
	}
}