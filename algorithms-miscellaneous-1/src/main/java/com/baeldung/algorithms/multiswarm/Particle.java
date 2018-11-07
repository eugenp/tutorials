package com.baeldung.algorithms.multiswarm;

import java.util.Arrays;

/**
 * Represents a particle, the basic component of a {@link Swarm}.
 * 
 * @author Donato Rimenti
 * 
 */
public class Particle {

	/**
	 * The current position of this particle.
	 */
	private long[] position;

	/**
	 * The speed of this particle.
	 */
	private long[] speed;

	/**
	 * The fitness of this particle for the current position.
	 */
	private double fitness;

	/**
	 * The best position found by this particle.
	 */
	private long[] bestPosition;

	/**
	 * The best fitness found by this particle.
	 */
	private double bestFitness = Double.NEGATIVE_INFINITY;

	/**
	 * Instantiates a new Particle.
	 *
	 * @param initialPosition
	 *            the initial {@link #position}
	 * @param initialSpeed
	 *            the initial {@link #speed}
	 */
	public Particle(long[] initialPosition, long[] initialSpeed) {
		this.position = initialPosition;
		this.speed = initialSpeed;
	}

	/**
	 * Gets the {@link #position}.
	 *
	 * @return the {@link #position}
	 */
	public long[] getPosition() {
		return position;
	}

	/**
	 * Gets the {@link #speed}.
	 *
	 * @return the {@link #speed}
	 */
	public long[] getSpeed() {
		return speed;
	}

	/**
	 * Gets the {@link #fitness}.
	 *
	 * @return the {@link #fitness}
	 */
	public double getFitness() {
		return fitness;
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
	 * Sets the {@link #position}.
	 *
	 * @param position
	 *            the new {@link #position}
	 */
	public void setPosition(long[] position) {
		this.position = position;
	}

	/**
	 * Sets the {@link #speed}.
	 *
	 * @param speed
	 *            the new {@link #speed}
	 */
	public void setSpeed(long[] speed) {
		this.speed = speed;
	}

	/**
	 * Sets the {@link #fitness}.
	 *
	 * @param fitness
	 *            the new {@link #fitness}
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
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
		temp = Double.doubleToLongBits(fitness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(position);
		result = prime * result + Arrays.hashCode(speed);
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
		Particle other = (Particle) obj;
		if (Double.doubleToLongBits(bestFitness) != Double.doubleToLongBits(other.bestFitness))
			return false;
		if (!Arrays.equals(bestPosition, other.bestPosition))
			return false;
		if (Double.doubleToLongBits(fitness) != Double.doubleToLongBits(other.fitness))
			return false;
		if (!Arrays.equals(position, other.position))
			return false;
		if (!Arrays.equals(speed, other.speed))
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
		return "Particle [position=" + Arrays.toString(position) + ", speed=" + Arrays.toString(speed) + ", fitness="
				+ fitness + ", bestPosition=" + Arrays.toString(bestPosition) + ", bestFitness=" + bestFitness + "]";
	}

}
