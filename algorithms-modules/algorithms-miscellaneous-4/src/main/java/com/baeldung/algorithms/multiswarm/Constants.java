package com.baeldung.algorithms.multiswarm;

/**
 * Constants used by the Multi-swarm optimization algorithms.
 * 
 * @author Donato Rimenti
 *
 */
public class Constants {

	/**
	 * The inertia factor encourages a particle to continue moving in its
	 * current direction.
	 */
	public static final double INERTIA_FACTOR = 0.729;

	/**
	 * The cognitive weight encourages a particle to move toward its historical
	 * best-known position.
	 */
	public static final double COGNITIVE_WEIGHT = 1.49445;

	/**
	 * The social weight encourages a particle to move toward the best-known
	 * position found by any of the particle’s swarm-mates.
	 */
	public static final double SOCIAL_WEIGHT = 1.49445;

	/**
	 * The global weight encourages a particle to move toward the best-known
	 * position found by any particle in any swarm.
	 */
	public static final double GLOBAL_WEIGHT = 0.3645;

	/**
	 * Upper bound for the random generation. We use it to reduce the
	 * computation time since we can rawly estimate it.
	 */
	public static final int PARTICLE_UPPER_BOUND = 10000000;

	/**
	 * Private constructor for utility class.
	 */
	private Constants() {
	}

}
