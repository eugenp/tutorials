package com.baeldung.r;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;

/**
 * Rserve showcase.
 * 
 * @author Donato Rimenti
 */
public class RserveMean {

    /**
     * Connects to the Rserve istance listening on 127.0.0.1:6311 and invokes the
     * customMean R function passing the given values as arguments.
     * 
     * @param values the input to the mean script
     * @return the result of the R script
     * @throws REngineException      if any error occurs
     * @throws REXPMismatchException if any error occurs
     */
    public double mean(int[] values) throws REngineException, REXPMismatchException {
        RConnection c = new RConnection();
        c.assign("input", values);
        return c.eval("customMean(input)")
            .asDouble();
    }

}