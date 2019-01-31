package integrationtests.junitrunner;

import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.runners.model.InitializationError;

import com.baeldung.springboot.application.SpringBootMainApplication;

/**
 * This JUnit runner is used for the integration tests.
 * See the package "integrationtests" under "src/test".
 *
 * Purpose:
 * It starts the Web-Application independently on a Tomcat web server as if it is done in a
 * production env, then fires the Declarative JSON-Tests against the End-Points.
 *
 * This thoroughly validates the end-to-end flow and all the Ports and Adapters wirings/injections.
 * :::Note::: This is very clean way of loading the App and there was no object mocking needed
 */
public class E2eJunitRunner extends ZeroCodeUnitRunner {

    static{
    	SpringBootMainApplication.start();
    }

    public E2eJunitRunner(Class<?> myclass) throws InitializationError {
        super(myclass);
    }
}
