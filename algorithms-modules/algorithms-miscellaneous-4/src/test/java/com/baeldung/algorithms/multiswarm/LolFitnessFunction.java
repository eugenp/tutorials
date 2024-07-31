package com.baeldung.algorithms.multiswarm;

/**
 * Specific fitness function implementation to solve the League of Legends
 * problem. This is the problem statement: <br>
 * <br>
 * In League of Legends, a player's Effective Health when defending against
 * physical damage is given by E=H(100+A)/100, where H is health and A is armor.
 * Health costs 2.5 gold per unit, and Armor costs 18 gold per unit. You have
 * 3600 gold, and you need to optimize the effectiveness E of your health and
 * armor to survive as long as possible against the enemy team's attacks. How
 * much of each should you buy? <br>
 * <br>
 * 
 * @author Donato Rimenti
 *
 */
public class LolFitnessFunction implements FitnessFunction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baeldung.algorithms.multiswarm.FitnessFunction#getFitness(long[])
	 */
	@Override
	public double getFitness(long[] particlePosition) {

		long health = particlePosition[0];
		long armor = particlePosition[1];

		// No negatives values accepted.
		if (health < 0 && armor < 0) {
			return -(health * armor);
		} else if (health < 0) {
			return health;
		} else if (armor < 0) {
			return armor;
		}

		// Checks if the solution is actually feasible provided our gold.
		double cost = (health * 2.5) + (armor * 18);
		if (cost > 3600) {
			return 3600 - cost;
		} else {
			// Check how good is the solution.
			long fitness = (health * (100 + armor)) / 100;
			return fitness;
		}
	}

}
