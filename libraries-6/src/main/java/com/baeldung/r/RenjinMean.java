package com.baeldung.r;

import org.renjin.script.RenjinScriptEngine;
import org.renjin.sexp.DoubleArrayVector;

import javax.script.ScriptException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Renjin showcase.
 * 
 * @author Donato Rimenti
 */
public class RenjinMean {

    /**
     * Invokes the customMean R function passing the given values as arguments.
     * 
     * @param values the input to the mean script
     * @return the result of the R script
     * @throws IOException        if any error occurs
     * @throws URISyntaxException if any error occurs
     * @throws ScriptException    if any error occurs
     */
    public double mean(int[] values) throws IOException, URISyntaxException, ScriptException {
        RenjinScriptEngine engine = new RenjinScriptEngine();
        String meanScriptContent = RUtils.getMeanScriptContent();
        engine.put("input", values);
        engine.eval(meanScriptContent);
        DoubleArrayVector result = (DoubleArrayVector) engine.eval("customMean(input)");
        return result.asReal();
    }

}