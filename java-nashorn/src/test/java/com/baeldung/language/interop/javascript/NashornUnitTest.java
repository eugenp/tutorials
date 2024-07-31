package com.baeldung.language.interop.javascript;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.script.*;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class NashornUnitTest {

    private ScriptEngine engine;

    @Before
    public void setUp() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    @Test
    public void trim() throws ScriptException {
        engine.eval(new InputStreamReader(NashornUnitTest.class.getResourceAsStream("/js/trim.js")));
    }

    @Test
    public void locations() throws ScriptException {
        engine.eval(new InputStreamReader(NashornUnitTest.class.getResourceAsStream("/js/locations.js")));
    }

    @Test
    public void bindProperties() throws ScriptException {
        engine.eval(new InputStreamReader(NashornUnitTest.class.getResourceAsStream("/js/bind.js")));
    }

    @Test
    public void magicMethods() throws ScriptException {
        engine.eval("var demo = load('classpath:js/no_such.js');" + "var tmp = demo.doesNotExist;" + "var none = demo.callNonExistingMethod()");
    }

    @Test
    public void typedArrays() throws ScriptException {
        engine.eval(new InputStreamReader(NashornUnitTest.class.getResourceAsStream("/js/typed_arrays.js")));
    }

    @Test
    public void basicUsage() throws ScriptException {
        Object result = engine.eval("var greeting='hello world';" + "print(greeting);" + "greeting");

        Assert.assertEquals("hello world", result);
    }

    @Test
    public void jsonObjectExample() throws ScriptException {
        Object obj = engine.eval("Java.asJSONCompatible({ number: 42, greet: 'hello', primes: [2,3,5,7,11,13] })");
        Map<String, Object> map = (Map<String, Object>) obj;

        Assert.assertEquals("hello", map.get("greet"));
        Assert.assertTrue(List.class.isAssignableFrom(map.get("primes").getClass()));
    }

    @Test
    public void tryCatchGuard() throws ScriptException {
        engine.eval("var math = loadWithNewGlobal('classpath:js/math_module.js');" + "math.failFunc();");
    }

    @Test
    public void extensionsExamples() throws ScriptException {
        String script = "var list = [1, 2, 3, 4, 5];" + "var result = '';" + "for each (var i in list) {" + "result+=i+'-';" + "};" + "print(result);";
        engine.eval(script);
    }

    @Test
    public void bindingsExamples() throws ScriptException {
        Bindings bindings = engine.createBindings();
        bindings.put("count", 3);
        bindings.put("name", "baeldung");

        String script = "var greeting='Hello ';" + "for(var i=count;i>0;i--) { " + "greeting+=name + ' '" + "}" + "greeting";

        Object bindingsResult = engine.eval(script, bindings);
        Assert.assertEquals("Hello baeldung baeldung baeldung ", bindingsResult);
    }

    @Test
    public void jvmBoundaryExamples() throws ScriptException, NoSuchMethodException {
        engine.eval("function composeGreeting(name) {" + "return 'Hello ' + name" + "}");

        Invocable invocable = (Invocable) engine;

        Object funcResult = invocable.invokeFunction("composeGreeting", "baeldung");
        Assert.assertEquals("Hello baeldung", funcResult);

        Object map = engine.eval("var HashMap = Java.type('java.util.HashMap');" + "var map = new HashMap();" + "map.put('hello', 'world');" + "map");

        Assert.assertTrue(Map.class.isAssignableFrom(map.getClass()));
    }

    @Test
    public void loadExamples() throws ScriptException {
        Object loadResult = engine.eval("load('classpath:js/script.js');" + "increment(5)");

        Assert.assertEquals(6, ((Double) loadResult).intValue());

        Object math = engine.eval("var math = loadWithNewGlobal('classpath:js/math_module.js');" + "math.increment(5);");

        Assert.assertEquals(6.0, math);
    }
}
