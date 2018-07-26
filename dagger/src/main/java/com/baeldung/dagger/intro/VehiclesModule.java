package com.baeldung.dagger.intro;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for providing vehicles components.
 * 
 * @author Donato Rimenti
 *
 */
@Module
public class VehiclesModule {

	/**
	 * Creates an {@link Engine}.
	 * 
	 * @return an {@link Engine}
	 */
	@Provides
	public Engine provideEngine() {
		return new Engine();
	}

	/**
	 * Creates a {@link Brand}.
	 * 
	 * @return a {@link Brand}
	 */
	@Provides
	@Singleton
	public Brand provideBrand() {
		return new Brand("Baeldung");
	}
}