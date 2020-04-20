package com.baeldung.r;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;

/**
 * Test for {@link RserveMean}.
 * 
 * @author Donato Rimenti
 */
@Ignore
public class RserveMeanIntegrationTest {

	/**
	 * Object to test.
	 */
	private RserveMean rserveMean = new RserveMean();

	/**
	 * Test for {@link RserveMeanIntegrationTest#mean(int[])}.
	 * 
	 * @throws REXPMismatchException if an error occurs
	 * @throws REngineException      if an error occurs
	 */
	@Test
	public void givenValues_whenMean_thenCorrect() throws REngineException, REXPMismatchException {
		int[] input = { 1, 2, 3, 4, 5 };
		double result = rserveMean.mean(input);
		Assert.assertEquals(3.0, result, 0.000001);
	}
}