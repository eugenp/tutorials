package com.baeldung;

import groovy.lang.*;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    private final ScriptEngine engineFromFactory;

    private App() throws IOException {
        loader = new GroovyClassLoader(this.getClass().getClassLoader());
        shell = new GroovyShell(loader, new Binding());
        engine = new GroovyScriptEngine(new URL[] {
          new File("src/main/groovy/com/baeldung/").toURI().toURL()
        }, this.getClass().getClassLoader());
        engineFromFactory = new GroovyScriptEngineFactory().getScriptEngine();
    }

    private void runCompiledClasses(int x, int y) {
        LOG.info("Executing {} + {}", x, y);
        Object result1 = new CalcScript().calcSum(x, y);
        LOG.info("Result of CalcScript.calcSum() method is {}", result1);

        Object result2 = new CalcMath().calcSum(x, y);
        LOG.info("Result of CalcMath.calcSum() method is {}", result2);
    }

    private void runDynamicShellScript(int x, int y) throws IOException {
        Script script = shell.parse(new File("src/main/groovy/com/baeldung/", "CalcScript.groovy"));
        LOG.info("Executing {} + {}", x, y);
        Object result = script.invokeMethod("calcSum", new Object[] { x, y });
        LOG.info("Result of CalcScript.calcSum() method is {}", result);
    }

    private void runDynamicClassWithLoader(int x, int y) throws IllegalAccessException, InstantiationException, IOException {
        Class calcClass = loader.parseClass(
          new File("src/main/groovy/com/baeldung/", "CalcMath.groovy"));
        GroovyObject calc = (GroovyObject) calcClass.newInstance();
        Object result = calc.invokeMethod("calcSum", new Object[] { x + 14, y + 14 });
        LOG.info("Result of CalcMath.calcSum() method is {}", result);
    }

    private void runDynamicClassWithEngine(int x, int y) throws IllegalAccessException,
      InstantiationException, ResourceException, ScriptException {

        Class<GroovyObject> calcClass = engine.loadScriptByName("CalcMath.groovy");
        GroovyObject calc = calcClass.newInstance();
        //WARNING the following will throw a ClassCastException
        //((CalcMath)calc).calcSum(1,2);
        Object result = calc.invokeMethod("calcSum", new Object[] { x, y });
        LOG.info("Result of CalcMath.calcSum() method is {}", result);
    }

    private void runDynamicClassWithEngineFactory(int x, int y) throws IllegalAccessException,
      InstantiationException, javax.script.ScriptException, FileNotFoundException {
        Class calcClas = (Class) engineFromFactory.eval(
          new FileReader(new File("src/main/groovy/com/baeldung/", "CalcMath.groovy")));
        GroovyObject calc = (GroovyObject) calcClas.newInstance();
        Object result = calc.invokeMethod("calcSum", new Object[] { x, y });
        LOG.info("Result of CalcMath.calcSum() method is {}", result);
    }

    private void runStaticCompiledClasses() {
        LOG.info("Running the Groovy classes compiled statically...");
        runCompiledClasses(5, 10);

    }

    private void runDynamicCompiledClasses() throws IOException, IllegalAccessException, InstantiationException,
      ResourceException, ScriptException, javax.script.ScriptException {
        LOG.info("Running a dynamic groovy script...");
        runDynamicShellScript(5, 10);
        LOG.info("Running a dynamic groovy class with GroovyClassLoader...");
        runDynamicClassWithLoader(10, 30);
        LOG.info("Running a dynamic groovy class with GroovyScriptEngine...");
        runDynamicClassWithEngine(15, 0);
        LOG.info("Running a dynamic groovy class with GroovyScriptEngine JSR223...");
        runDynamicClassWithEngineFactory(5, 6);
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
      ResourceException, ScriptException, IOException, javax.script.ScriptException {
        App app = new App();
        app.runStaticCompiledClasses();
        app.runDynamicCompiledClasses();
    }
}
