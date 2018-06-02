package org.baeldung.boot;

import java.util.HashMap;
import java.util.Map;

import org.baeldung.boot.DemoApplication;
import org.baeldung.boot.model.Foo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class FooIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void givenInquiryingFooWithId_whenIdIsValid_thenHttpStatusOK() {
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", "1");
        ResponseEntity<Foo> fooResponse = testRestTemplate.getForEntity("/{id}", Foo.class, pathVariables);
        Assert.assertNotNull(fooResponse);
        Assert.assertEquals(HttpStatus.OK, fooResponse.getStatusCode());
    }

    @Test
    public void givenInquiryingFooWithName_whenNameIsValid_thenHttpStatusOK() {
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("name", "Foo_Name");
        ResponseEntity<Foo> fooResponse = testRestTemplate.getForEntity("/?name={name}", Foo.class, pathVariables);
        Assert.assertNotNull(fooResponse);
        Assert.assertEquals(HttpStatus.OK, fooResponse.getStatusCode());
    }
}