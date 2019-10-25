package com.baeldung.jeekotlin;

import com.baeldung.jeekotlin.entity.Student;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class StudentResourceIntegrationTest {

    @Deployment
    public static WebArchive createDeployment() {
        JavaArchive[] kotlinRuntime = Maven.configureResolver()
                .workOffline()
                .withMavenCentralRepo(true)
                .withClassPathResolution(true)
                .loadPomFromFile("pom.xml")
                .resolve("org.jetbrains.kotlin:kotlin-stdlib")
                .withTransitivity()
                .as(JavaArchive.class);

        return ShrinkWrap.create(WebArchive.class, "kotlin.war")
                .addPackages(true, Filters.exclude(".*Test*"),
                        "com.baeldung.jeekotlin"
                )
                .addAsLibraries(kotlinRuntime)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @RunAsClient
    public void when_post__then_return_ok(@ArquillianResource URL url) throws URISyntaxException, JsonProcessingException {
        String student = new ObjectMapper().writeValueAsString(new Student("firstName", "lastName"));
        WebTarget webTarget = ClientBuilder.newClient().target(url.toURI());

        Response response = webTarget
                .path("/student")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(student));

        assertEquals(200, response.getStatus());
    }

    @Test
    @RunAsClient
    public void when_get__then_return_ok(@ArquillianResource URL url) throws URISyntaxException, JsonProcessingException {
        WebTarget webTarget = ClientBuilder.newClient().target(url.toURI());

        Response response = webTarget
                .path("/student/1")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(200, response.getStatus());
    }

    @Test
    @RunAsClient
    public void when_put__then_return_ok(@ArquillianResource URL url) throws URISyntaxException, JsonProcessingException {
        Student student = new Student("firstName", "lastName");
        student.setId(1L);
        String studentJson = new ObjectMapper().writeValueAsString(student);
        WebTarget webTarget = ClientBuilder.newClient().target(url.toURI());

        Response response = webTarget
                .path("/student")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(studentJson));

        assertEquals(200, response.getStatus());
    }

    @Test
    @RunAsClient
    public void when_delete__then_return_ok(@ArquillianResource URL url) throws URISyntaxException, JsonProcessingException {
        WebTarget webTarget = ClientBuilder.newClient().target(url.toURI());

        Response response = webTarget
                .path("/student/1")
                .request()
                .delete();

        assertEquals(204, response.getStatus());
    }

}