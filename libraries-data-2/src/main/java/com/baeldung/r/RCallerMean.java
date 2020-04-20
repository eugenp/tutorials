package com.baeldung.r;

import java.io.IOException;
import java.net.URISyntaxException;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCallerOptions;
import com.github.rcaller.rstuff.RCode;

/**
 * RCaller showcase.
 * 
 * @author Donato Rimenti
 */
public class RCallerMean {

	/**
	 * Invokes the customMean R function passing the given values as arguments.
	 * 
	 * @param values the input to the mean script
	 * @return the result of the R script
	 * @throws IOException        if any error occurs
	 * @throws URISyntaxException if any error occurs
	 */
	public double mean(int[] values) throws IOException, URISyntaxException {
		String fileContent = RUtils.getMeanScriptContent();
		RCode code = RCode.create();
		code.addRCode(fileContent);
		code.addIntArray("input", values);
		code.addRCode("result <- customMean(input)");
		RCaller caller = RCaller.create(code, RCallerOptions.create());
		caller.runAndReturnResult("result");
		return caller.getParser().getAsDoubleArray("result")[0];
	}

}