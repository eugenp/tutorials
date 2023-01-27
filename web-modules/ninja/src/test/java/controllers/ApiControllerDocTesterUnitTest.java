package controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;
import ninja.NinjaDocTester;

<<<<<<< HEAD:web-modules/ninja/src/test/java/controllers/ApiControllerDocTesterIntegrationTest.java
public class ApiControllerDocTesterIntegrationTest extends NinjaDocTester {
=======
public class ApiControllerDocTesterUnitTest extends NinjaDocTester {
>>>>>>> cb71de5be1a61279b4ac230c5ddde1cd1017ea42:web-modules/ninja/src/test/java/controllers/ApiControllerDocTesterUnitTest.java
    
    String URL_INDEX = "/";
    String URL_HELLO = "/hello";

    @Test
    public void testGetIndex() {
        Response response = makeRequest(Request.GET().url(testServerUrl().path(URL_INDEX)));
        assertThat(response.payload, containsString("Hello, welcome to Ninja Framework!"));
    }
    
    @Test
    public void testGetHello() {
        Response response = makeRequest(Request.GET().url(testServerUrl().path(URL_HELLO)));
        assertThat(response.payload, containsString("Bonjour, bienvenue dans Ninja Framework!"));
    }

}
