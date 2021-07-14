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
        Expression expression = new ExpressionBuilder("3x+2y").variables("x", "y")
          .build()
          .setVariable("x", 2)
          .setVariable("y", 3);
        double result = expression.evaluate();
        Assertions.assertEquals(12, result);
    }
    
    @Test
    public void givenMathFunctions_whenCallEvaluateMethod_thenSuccess() {
        Expression expression = new ExpressionBuilder("sin(x)*sin(x)+cos(x)*cos(x)").variables("x")
          .build()
          .setVariable("x", 0.5);
        double result = expression.evaluate();
        Assertions.assertEquals(1, result);
    }

    @Test
    public void givenExpression_whenCallEvaluateMethod_thenSuccess() {
        String expression = "3+2";
        DoubleEvaluator eval = new DoubleEvaluator();
        Double result = eval.evaluate(expression);
        Assertions.assertEquals(5, result);
    } 
    
    @Test
    public void givenVariables_whenCallEvaluateMethod_thenSuccess() {
        String expression = "3*x+2*y";
        DoubleEvaluator eval = new DoubleEvaluator();
        StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
        variables.set("x", 2.0);
        variables.set("y", 3.0);
        Double result = eval.evaluate(expression, variables);
        Assertions.assertEquals(12, result);
    }

    @Test
    public void givenMathFunction_whenCallEvaluateMethod_thenSuccess() {
        String expression = "sin(x)*sin(x)+cos(x)*cos(x)";
        DoubleEvaluator eval = new DoubleEvaluator();
        StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
        variables.set("x", 0.5);
        Double result = eval.evaluate(expression, variables);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void givenJavaScriptingApiAndSimpleExpression_whenCallEvalMethod_thenSuccess() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        String expression = "3+2";
        Integer result = (Integer) scriptEngine.eval(expression);
        Assertions.assertEquals(5, result);
    }
    
    @Test
    public void givenJavaScriptingApi_whenCallEvalMethod_thenSuccess() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        String expression = "x=2; y=3; 3*x+2*y;";
        Double result = (Double) scriptEngine.eval(expression);
        Assertions.assertEquals(12, result);
    }
    
}
