package com.baeldung.jeekotlin.rest;

import org.jboss.arquillian.junit.Arquillian;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;
import com.baeldung.jeekotlin.entity.Student;
import javax.ws.rs.client.Entity;

@RunWith(Arquillian.class)
public class StudentResourceIntegrationTest {

    public static final Logger LOG = LoggerFactory.getLogger(StudentResourceIntegrationTest.class);

    @Deployment(testable=false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, Filters.exclude(".*Test*"), new String[] {
                        "com.baeldung.jeekotlin",
                        "kotlin.collections",
                        "kotlin.jvm.internal"
                })
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @RunAsClient
    public void when_post_then_responseOk(@ArquillianResteasyResource WebTarget webTarget) {
        final Response response = webTarget
                .path("/student")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(new Student("1","1")));
        assertEquals(200, response.getStatus());
    }
 }

