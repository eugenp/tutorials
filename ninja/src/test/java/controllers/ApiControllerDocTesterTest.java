package controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;
import ninja.NinjaDocTester;

public class ApiControllerDocTesterUnitTest extends NinjaDocTester {
    
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
