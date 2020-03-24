package com.baeldung.dagger.intro;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger component for building vehicles.
 * 
 * @author Donato Rimenti
 *
 */
@Singleton
@Component(modules = VehiclesModule.class)
public interface VehiclesComponent {

	/**
	 * Builds a {@link Car}.
	 * 
	 * @return a {@link Car}
	 */
	public Car buildCar();

}