package com.baeldung.annotation.servletcomponentscan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.baeldung.annotation.servletcomponentscan.javaee.AttrListener;
import com.baeldung.annotation.servletcomponentscan.javaee.EchoServlet;
import com.baeldung.annotation.servletcomponentscan.javaee.HelloFilter;
import com.baeldung.annotation.servletcomponentscan.javaee.HelloServlet;

@RunWith(Arquillian.class)
public class JavaEEAppIntegrationTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).addClass(JavaEEApp.class).addClasses(AttrListener.class, HelloFilter.class, HelloServlet.class, EchoServlet.class);
    }

    @Inject
    private ServletContext servletContext;

    @Test
    public void givenServletContextListener_whenAccessSpecialAttrs_thenFound() throws MalformedURLException {
        assertNotNull(servletContext);
        assertNotNull(servletContext.getAttribute("servlet-context-attr"));
        assertEquals("test", servletContext.getAttribute("servlet-context-attr"));
    }

    @Test
    public void givenServletContext_whenCheckHelloFilterMappings_thenCorrect() throws MalformedURLException {
        assertNotNull(servletContext);
        FilterRegistration filterRegistration = servletContext.getFilterRegistration("hello filter");

        assertNotNull(filterRegistration);
        assertTrue(filterRegistration.getServletNameMappings().contains("echo servlet"));
    }

    @ArquillianResource
    private URL base;

    @Test
    @RunAsClient
    public void givenFilterAndServlet_whenGetHello_thenRespondFilteringHello() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI.create(new URL(base, "hello").toExternalForm()));
        Response response = target.request().get();

        assertEquals("filtering hello", response.readEntity(String.class));
    }

    @Test
    @RunAsClient
    public void givenFilterAndServlet_whenPostEcho_thenEchoFiltered() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI.create(new URL(base, "echo").toExternalForm()));
        Response response = target.request().post(Entity.entity("echo", MediaType.TEXT_PLAIN_TYPE));

        assertEquals("filtering echo", response.readEntity(String.class));
    }

}
