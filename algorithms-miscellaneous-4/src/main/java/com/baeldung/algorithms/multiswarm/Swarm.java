package com.baeldung.algorithms.multiswarm;

import java.util.Arrays;
import java.util.Random;

/**
 * Represents a collection of {@link Particle}.
 * 
 * @author Donato Rimenti
 *
 */
public class Swarm {

	/**
	 * The particles of this swarm.
	 */
	private Particle[] particles;

	/**
	 * The best position found within the particles of this swarm.
	 */
	private long[] bestPosition;

	/**
	 * The best fitness score found within the particles of this swarm.
	 */
	private double bestFitness = Double.NEGATIVE_INFINITY;

	/**
	 * A random generator.
	 */
	private Random random = new Random();

	/**
	 * Instantiates a new Swarm.
	 *
	 * @param numParticles
	 *            the number of particles of the swarm
	 */
	public Swarm(int numParticles) {
		particles = new Particle[numParticles];
		for (int i = 0; i < numParticles; i++) {
			long[] initialParticlePosition = { random.nextInt(Constants.PARTICLE_UPPER_BOUND),
					random.nextInt(Constants.PARTICLE_UPPER_BOUND) };
			long[] initialParticleSpeed = { random.nextInt(Constants.PARTICLE_UPPER_BOUND),
					random.nextInt(Constants.PARTICLE_UPPER_BOUND) };
			particles[i] = new Particle(initialParticlePosition, initialParticleSpeed);
		}
	}

	/**
	 * Gets the {@link #particles}.
	 *
	 * @return the {@link #particles}
	 */
	public Particle[] getParticles() {
		return particles;
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

	/**
	 * Sets the {@link #bestPosition}.
	 *
	 * @param bestPosition
	 *            the new {@link #bestPosition}
	 */
	public void setBestPosition(long[] bestPosition) {
		this.bestPosition = bestPosition;
	}

	/**
	 * Sets the {@link #bestFitness}.
	 *
	 * @param bestFitness
	 *            the new {@link #bestFitness}
	 */
	public void setBestFitness(double bestFitness) {
		this.bestFitness = bestFitness;
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
		result = prime * result + Arrays.hashCode(particles);
		result = prime * result + ((random == null) ? 0 : random.hashCode());
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
		Swarm other = (Swarm) obj;
		if (Double.doubleToLongBits(bestFitness) != Double.doubleToLongBits(other.bestFitness))
			return false;
		if (!Arrays.equals(bestPosition, other.bestPosition))
			return false;
		if (!Arrays.equals(particles, other.particles))
			return false;
		if (random == null) {
			if (other.random != null)
				return false;
		} else if (!random.equals(other.random))
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
		return "Swarm [particles=" + Arrays.toString(particles) + ", bestPosition=" + Arrays.toString(bestPosition)
				+ ", bestFitness=" + bestFitness + ", random=" + random + "]";
	}

}
