package com.baeldung.r;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * FastR showcase.
 * 
 * @author Donato Rimenti
 */
public class FastRMean {

	/**
	 * Invokes the customMean R function passing the given values as arguments.
	 * 
	 * @param values the input to the mean script
	 * @return the result of the R script
	 */
	public double mean(int[] values) {
		Context polyglot = Context.newBuilder().allowAllAccess(true).build();
		String meanScriptContent = RUtils.getMeanScriptContent();
		polyglot.eval("R", meanScriptContent);
		Value rBindings = polyglot.getBindings("R");
		Value rInput = rBindings.getMember("c").execute(values);
		return rBindings.getMember("customMean").execute(rInput).asDouble();
	}

}