package com.baeldung.scripting;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Nashorn {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        Object result = engine.eval(
                "var greeting='hello world';" +
                        "print(greeting);" +
                        "greeting");

        System.out.println(result);

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
}
