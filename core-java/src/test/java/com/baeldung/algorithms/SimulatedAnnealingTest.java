package com.baeldung.algorithms;

import org.junit.Assert;
import org.junit.Test;

public class SimulatedAnnealingTest {

	@Test
	public void testSimulateAnnealing() {
		Assert.assertTrue(SimulatedAnnealing.simulateAnnealing(10, 1000, 0.9) > 0);
	}

}
