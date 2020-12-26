package com.baeldung.math.evaluate;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class EvalauteMathExpressionsUnitTest {

    @Test
    public void givenSimpleExpression_whenCallEvaluateMethod_thenSuccess() {
        Expression expression = new ExpressionBuilder("3+2").build();
        double result = expression.evaluate();
        Assertions.assertEquals(5, result);
    }
    
    @Test
    public void givenTwoVariables_whenCallEvaluateMethod_thenSuccess() {
        Expression expression = new ExpressionBuilder("3x+2y")
          .variables("x", "y")
          .build()
          .setVariable("x", 2)
          .setVariable("y", 3);
        double result = expression.evaluate();
        Assertions.assertEquals(12, result);
    }
    
    @Test
    public void givenVariables_whenCallEvaluateMethod_thenSuccess() {
        String expression = "x+y";
        DoubleEvaluator eval = new DoubleEvaluator();
        StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
        variables.set("x", 3.0);
        variables.set("y", 2.0);
        Double result = eval.evaluate(expression, variables);
        Assertions.assertEquals(5, result);
    }
    
    @Test
    public void givenJavaScriptingApi_whenCallEvalMethod_thenSuccess() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        String expression = "3+2";
        Integer result = (Integer) scriptEngine.eval(expression);
        Assertions.assertEquals(5, result);
    }
}
