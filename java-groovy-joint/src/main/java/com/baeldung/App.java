package com.baeldung;

import groovy.lang.*;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class App {
    private final static Logger LOG = LoggerFactory.getLogger(App.class);
    private final GroovyClassLoader loader;
    private final GroovyShell shell;
    private final GroovyScriptEngine engine;

    private App() throws IOException {
        loader = new GroovyClassLoader(this.getClass().getClassLoader());
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass("com.baeldung.CalcScript");
        shell = new GroovyShell(loader, new Binding(), config);
        engine = new GroovyScriptEngine(new URL[] { new File("src/main/groovy/com/baeldung/").toURI().toURL() }, this.getClass().getClassLoader());
    }

    private void runCompiledClasses(int x, int y) {
        Object result1 = new CalcScript().calcSum(x, y);
        LOG.info("Result of calcSum() method is {}", result1);

        Object result2 = new CalcMath().calcSum(x, y);
        LOG.info("Result of calcSum() method is {}", result2);
    }

    private void runShellScript(int x, int y) {
        Script script = shell.parse(String.format("calcSum(%d,%d)", x, y));
        assert script instanceof CalcScript;
        Object result = script.run();
        LOG.info("Result of run() method is {}", result);

        Object script2 = shell.parse("CalcScript");
        assert script2 instanceof CalcScript;
        Object result2 = ((CalcScript) script2).calcSum(x + 7, y + 7);
        LOG.info("Result of calcSum() method is {}", result2);

        Script script3 = shell.parse("");
        assert script3 instanceof CalcScript;
        Object result3 = script3.invokeMethod("calcSum", new Object[] { x + 14, y + 14 });
        LOG.info("Result of run() method is {}", result3);

    }

    private void runClassWithLoader(int x, int y) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class calcClass = loader.loadClass("com.baeldung.CalcMath");
        Object calc = calcClass.newInstance();
        assert calc instanceof CalcMath;

        Object result = ((CalcMath) calc).calcSum(x, y);
        LOG.info("Result is {}", result);

        Object result2 = ((GroovyObject) calc).invokeMethod("calcSum", new Object[] { x + 14, y + 14 });
        LOG.info("Result is {}", result2);

    }

    private void runClassWithEngine(int x, int y) throws ClassNotFoundException, IllegalAccessException, InstantiationException, ResourceException, ScriptException {

        Class<GroovyObject> calcClass = engine.loadScriptByName("CalcMath.groovy");
        GroovyObject calc = calcClass.newInstance();
        Object result = calc.invokeMethod("calcSum", new Object[] { x, y });
        //WARNING the following will throw a ClassCastException
        //((CalcMath)calc).calcSum(1,2);
        LOG.info("Result is {}", result);
    }

    private void runClassWithEngineFactory(int x, int y) throws ClassNotFoundException, IllegalAccessException, InstantiationException, ResourceException, ScriptException, javax.script.ScriptException {
        ScriptEngine engine = new GroovyScriptEngineFactory().getScriptEngine();
        Class calcClass = (Class) ((Compilable) engine).compile("com.baeldung.CalcMath").eval();
        Object calc = calcClass.newInstance();
        Object result = ((CalcMath) calc).calcSum(1, 20);
        LOG.info("Result is {}", result);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ResourceException, ScriptException, IOException, javax.script.ScriptException {
        App app = new App();
        LOG.info("Running an already compiled groovy class instance...");
        app.runCompiledClasses(5, 10);
        LOG.info("Running a groovy script...");
        app.runShellScript(5, 10);
        LOG.info("Running a groovy class...");
        app.runClassWithLoader(1, 3);
        LOG.info("Running a groovy class using the engine...");
        app.runClassWithEngine(10, 30);
        LOG.info("Running a groovy class using the engine factory...");
        app.runClassWithEngineFactory(10, 30);
    }
}
