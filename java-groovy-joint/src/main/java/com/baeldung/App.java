package com.baeldung;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {
    private final static Logger LOG = LoggerFactory.getLogger(App.class);
    private final GroovyClassLoader loader;
    private final GroovyShell shell;

    private App() {
        loader = new GroovyClassLoader(this.getClass().getClassLoader());
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass("com.baeldung.CalcScript");
        shell = new GroovyShell(loader, new Binding(), config);
    }

    private void runScript(int x, int y) {
        Object script = shell.parse(String.format("calcSum(%d,%d)", x, y));
        assert script instanceof CalcScript;
        Object result = ((CalcScript) script).run();
        LOG.info("Result of run() method is {}", result);

        Object script2 = shell.parse("CalcScript");
        Object result2 = ((CalcScript) script2).calcSum(x + 7, y + 7);
        LOG.info("Result of calcSum() method is {}", result2);

    }

    private void runClass(int x, int y) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class calcClass = loader.loadClass("com.baeldung.CalcMath");
        Object calc = calcClass.newInstance();
        assert calc instanceof CalcMath;

        Object result = ((CalcMath) calc).calcSum(x, y);
        LOG.info("Result is {}", result);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        App app = new App();
        LOG.info("Running a groovy script...");
        app.runScript(5, 10);
        LOG.info("Running a groovy class...");
        app.runClass(1, 3);
    }
}
