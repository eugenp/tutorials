package org.baeldung.embedded;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class EmbeddedTomcatRunner extends BlockJUnit4ClassRunner {

    public EmbeddedTomcatRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    // use one static Tomcat instance shared across all tests
    private static EmbeddedTomcatApp embeddedTomcatApp = new EmbeddedTomcatApp();

    @Override
    protected Statement classBlock(RunNotifier notifier) {
        ensureSharedTomcatStarted();
        Statement result = super.classBlock(notifier);
        return result;
    }

    private void ensureSharedTomcatStarted() {
        if (!embeddedTomcatApp.isStarted()) {
            try {
                embeddedTomcatApp.start();
            } catch (Exception e) {
                throw new RuntimeException("Error while starting embedded Tomcat server", e);
            }
        }
    }

    @Override
    protected Object createTest() throws Exception {
        if (!embeddedTomcatApp.isStartedSucessfully()) {
            throw new RuntimeException("Tomcat server not started successfully. Skipping test");
        }
        Object testInstance = super.createTest();
        embeddedTomcatApp.getWebApplicationContext()
            .getAutowireCapableBeanFactory()
            .autowireBean(testInstance);
        return testInstance;
    }
}