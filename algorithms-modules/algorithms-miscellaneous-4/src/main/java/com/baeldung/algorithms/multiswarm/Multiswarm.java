package com.baeldung.algorithms.multiswarm;

import java.util.Arrays;
import java.util.Random;

/**
 * Represents a collection of {@link Swarm}.
 * 
 * @author Donato Rimenti
 *
 */
public class Multiswarm {

	/**
	 * The swarms managed by this multiswarm.
	 */
	private Swarm[] swarms;

	/**
	 * The best position found within all the {@link #swarms}.
	 */
	private long[] bestPosition;

	/**
	 * The best fitness score found within all the {@link #swarms}.
	 */
	private double bestFitness = Double.NEGATIVE_INFINITY;

	/**
	 * A random generator.
	 */
	private Random random = new Random();

	/**
	 * The fitness function used to determine how good is a particle.
	 */
	private FitnessFunction fitnessFunction;

	/**
	 * Instantiates a new Multiswarm.
	 * 
	 * @param numSwarms
	 *            the number of {@link #swarms}
	 * @param particlesPerSwarm
	 *            the number of particle for each {@link #swarms}
	 * @param fitnessFunction
	 *            the {@link #fitnessFunction}
	 */
	public Multiswarm(int numSwarms, int particlesPerSwarm, FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
		this.swarms = new Swarm[numSwarms];
		for (int i = 0; i < numSwarms; i++) {
			swarms[i] = new Swarm(particlesPerSwarm);
		}
	}

	/**
	 * Main loop of the algorithm. Iterates all particles of all
	 * {@link #swarms}. For each particle, computes the new fitness and checks
	 * if a new best position has been found among itself, the swarm and all the
	 * swarms and finally updates the particle position and speed.
	 */
	public void mainLoop() {
		for (Swarm swarm : swarms) {
			for (Particle particle : swarm.getParticles()) {

				long[] particleOldPosition = particle.getPosition().clone();

				// Calculate the particle fitness.
				particle.setFitness(fitnessFunction.getFitness(particleOldPosition));

				// Check if a new best position has been found for the particle
				// itself, within the swarm and the multiswarm.
				if (particle.getFitness() > particle.getBestFitness()) {
					particle.setBestFitness(particle.getFitness());
					particle.setBestPosition(particleOldPosition);

					if (particle.getFitness() > swarm.getBestFitness()) {
						swarm.setBestFitness(particle.getFitness());
						swarm.setBestPosition(particleOldPosition);

						if (swarm.getBestFitness() > bestFitness) {
							bestFitness = swarm.getBestFitness();
							bestPosition = swarm.getBestPosition().clone();
						}

					}
				}

				// Updates the particle position by adding the speed to the
				// actual position.
				long[] position = particle.getPosition();
				long[] speed = particle.getSpeed();

				position[0] += speed[0];
				position[1] += speed[1];

				// Updates the particle speed.
				speed[0] = getNewParticleSpeedForIndex(particle, swarm, 0);
				speed[1] = getNewParticleSpeedForIndex(particle, swarm, 1);
			}
		}
	}

	/**
	 * Computes a new speed for a given particle of a given swarm on a given
	 * axis. The new speed is computed using the formula:
	 * 
	 * <pre>
	 * ({@link Constants#INERTIA_FACTOR} * {@link Particle#getSpeed()}) + 
	 * (({@link Constants#COGNITIVE_WEIGHT} * random(0,1)) * ({@link Particle#getBestPosition()} - {@link Particle#getPosition()})) +
	 * (({@link Constants#SOCIAL_WEIGHT} * random(0,1)) * ({@link Swarm#getBestPosition()} - {@link Particle#getPosition()})) + 
	 * (({@link Constants#GLOBAL_WEIGHT} * random(0,1)) * ({@link #bestPosition} - {@link Particle#getPosition()}))
	 * </pre>
	 *
	 * @param particle
	 *            the particle whose new speed needs to be computed
	 * @param swarm
	 *            the swarm which contains the particle
	 * @param index
	 *            the index of the particle axis whose speeds needs to be
	 *            computed
	 * @return the new speed of the particle passed on the given axis
	 */
	private int getNewParticleSpeedForIndex(Particle particle, Swarm swarm, int index) {
		return (int) ((Constants.INERTIA_FACTOR * particle.getSpeed()[index])
				+ (randomizePercentage(Constants.COGNITIVE_WEIGHT)
						* (particle.getBestPosition()[index] - particle.getPosition()[index]))
				+ (randomizePercentage(Constants.SOCIAL_WEIGHT)
						* (swarm.getBestPosition()[index] - particle.getPosition()[index]))
				+ (randomizePercentage(Constants.GLOBAL_WEIGHT)
						* (bestPosition[index] - particle.getPosition()[index])));
	}

	/**
	 * Returns a random number between 0 and the value passed as argument.
	 *
	 * @param value
	 *            the value to randomize
	 * @return a random value between 0 and the one passed as argument
	 */
	private double randomizePercentage(double value) {
		return random.nextDouble() * value;
	}

	/**
	 * Gets the {@link #bestPosition}.
	 *
	 * @return the {@link #bestPosition}
	 */
	public long[] getBestPosition() {
		return bestPosition;
	}

	/**
	 * Gets the {@link #bestFitness}.
	 *
	 * @return the {@link #bestFitness}
	 */
	public double getBestFitness() {
		return bestFitness;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bestFitness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(bestPosition);
		result = prime * result + ((fitnessFunction == null) ? 0 : fitnessFunction.hashCode());
		result = prime * result + ((random == null) ? 0 : random.hashCode());
		result = prime * result + Arrays.hashCode(swarms);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Multiswarm other = (Multiswarm) obj;
		if (Double.doubleToLongBits(bestFitness) != Double.doubleToLongBits(other.bestFitness))
			return false;
		if (!Arrays.equals(bestPosition, other.bestPosition))
			return false;
		if (fitnessFunction == null) {
			if (other.fitnessFunction != null)
				return false;
		} else if (!fitnessFunction.equals(other.fitnessFunction))
			return false;
		if (random == null) {
			if (other.random != null)
				return false;
		} else if (!random.equals(other.random))
			return false;
		if (!Arrays.equals(swarms, other.swarms))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Multiswarm [swarms=" + Arrays.toString(swarms) + ", bestPosition=" + Arrays.toString(bestPosition)
				+ ", bestFitness=" + bestFitness + ", random=" + random + ", fitnessFunction=" + fitnessFunction + "]";
	}

}
