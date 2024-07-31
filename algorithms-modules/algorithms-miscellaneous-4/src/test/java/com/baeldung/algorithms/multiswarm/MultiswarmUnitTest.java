package com.baeldung.algorithms.multiswarm;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.baeldung.algorithms.support.MayFailRule;

/**
 * Test for {@link Multiswarm}.
 * 
 * @author Donato Rimenti
 * 
 */
public class MultiswarmUnitTest {

	/**
	 * Rule for handling expected failures. We use this since this test may
	 * actually fail due to bad luck in the random generation.
	 */
	@Rule
	public MayFailRule mayFailRule = new MayFailRule();

	/**
	 * Tests the multiswarm algorithm with a generic problem. The problem is the
	 * following: <br>
	 * <br>
	 * In League of Legends, a player's Effective Health when defending against
	 * physical damage is given by E=H(100+A)/100, where H is health and A is
	 * armor. Health costs 2.5 gold per unit, and Armor costs 18 gold per unit.
	 * You have 3600 gold, and you need to optimize the effectiveness E of your
	 * health and armor to survive as long as possible against the enemy team's
	 * attacks. How much of each should you buy? <br>
	 * <br>
	 * The solution is H = 1080, A = 50 for a total fitness of 1620. Tested with
	 * 50 swarms each with 1000 particles.
	 */
	@Test
	public void givenMultiswarm_whenThousandIteration_thenSolutionFound() {
		Multiswarm multiswarm = new Multiswarm(50, 1000, new LolFitnessFunction());

		// Iterates 1000 times through the main loop and prints the result.
		for (int i = 0; i < 1000; i++) {
			multiswarm.mainLoop();
		}

		System.out.println("Best fitness found: " + multiswarm.getBestFitness() + "[" + multiswarm.getBestPosition()[0]
				+ "," + multiswarm.getBestPosition()[1] + "]");
		Assert.assertEquals(1080, multiswarm.getBestPosition()[0]);
		Assert.assertEquals(50, multiswarm.getBestPosition()[1]);
		Assert.assertEquals(1620, (int) multiswarm.getBestFitness());
	}

}
