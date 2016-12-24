package com.baeldung.scripting;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Nashorn {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        Object result = engine.eval(
                "var greeting='hello world';" +
                        "print(greeting);" +
                        "greeting");

        System.out.println(result);

        bindingsExamples(engine);

        jvmBoundaryExamples(engine);

        extensionsExamples(engine);

        loadExamples(engine);

        tryCatchGuard(engine);

        engine.eval(new InputStreamReader(Nashorn.class.getResourceAsStream("/js/typed_arrays.js")));

        engine.eval("var demo = load('classpath:js/no_such.js');" +
                "var tmp = demo.doesNotExist;" +
                "var none = demo.callNonExistingMethod()");

        engine.eval(new InputStreamReader(Nashorn.class.getResourceAsStream("/js/bind.js")));

        engine.eval(new InputStreamReader(Nashorn.class.getResourceAsStream("/js/locations.js")));

        engine.eval(new InputStreamReader(Nashorn.class.getResourceAsStream("/js/trim.js")));

        jsonObjectExample(engine);

    }

    private static void jsonObjectExample(ScriptEngine engine) throws ScriptException {
        Object obj = engine.eval("Java.asJSONCompatible({ number: 42, greet: 'hello', primes: [2,3,5,7,11,13] })");
        Map<String, Object> map = (Map<String, Object>)obj;
        System.out.println(map.get("greet"));
        System.out.println(List.class.isAssignableFrom(map.get("primes").getClass()));
    }

    private static void tryCatchGuard(ScriptEngine engine) throws ScriptException {
        engine.eval("var math = loadWithNewGlobal('classpath:js/math_module.js');" +
                "math.failFunc();");
    }

    private static void extensionsExamples(ScriptEngine engine) throws ScriptException {
        String script = "var list = [1, 2, 3, 4, 5];" +
                "var result = '';" +
                "for each (var i in list) {" +
                "result+=i+'-';" +
                "};" +
                "print(result);";
        engine.eval(script);
    }

    private static void bindingsExamples(ScriptEngine engine) throws ScriptException {
        Bindings bindings = engine.createBindings();
        bindings.put("count", 3);
        bindings.put("name", "baeldung");

        String script = "var greeting='Hello ';" +
                "for(var i=count;i>0;i--) { " +
                "greeting+=name + ' '" +
                "}" +
                "greeting";

        Object bindingsResult = engine.eval(script, bindings);
        System.out.println(bindingsResult);
    }

    private static void jvmBoundaryExamples(ScriptEngine engine) throws ScriptException, NoSuchMethodException {
        engine.eval("function composeGreeting(name) {" +
                "return 'Hello ' + name" +
                "}");

        Invocable invocable = (Invocable) engine;

        Object funcResult = invocable.invokeFunction("composeGreeting", "baeldung");
        System.out.println(funcResult);

        Object map = engine.eval("var HashMap = Java.type('java.util.HashMap');" +
                "var map = new HashMap();" +
                "map.put('hello', 'world');" +
                "map");

        System.out.println(map);
    }

    private static void loadExamples(ScriptEngine engine) throws ScriptException {
        Object loadResult = engine.eval("load('classpath:js/script.js');" +
                "increment(5)");

        System.out.println(loadResult);

        Object math = engine.eval("var math = loadWithNewGlobal('classpath:js/math_module.js');" +
                "math.increment(5);");

        System.out.println(math);
    }
}
