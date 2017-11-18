package org.baeldung.boot;

import org.baeldung.boot.components.FooService;
import org.baeldung.boot.model.Foo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(
  classes = DemoApplication.class,
  webEnvironment = WebEnvironment.RANDOM_PORT)
public class FooComponentIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @SpyBean
    private FooService fooService;

    @Before
    public void init() throws Exception {
        Foo foo = new Foo();
        foo.setId(5);
        foo.setName("MOCKED_FOO");

        doReturn(foo).when(fooService).getFooWithId(anyInt());

        // doCallRealMethod().when(fooComponent).getFooWithName(anyString());
    }

    @Test
    public void givenInquiryingFooWithId_whenFooComponentIsMocked_thenAssertMockedResult() {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", "1");
        ResponseEntity<Foo> fooResponse = testRestTemplate.getForEntity("/{id}", Foo.class, pathVariables);

        assertNotNull(fooResponse);
        assertEquals(HttpStatus.OK, fooResponse.getStatusCode());
        assertEquals(5, fooResponse.getBody().getId().longValue());
        assertEquals("MOCKED_FOO", fooResponse.getBody().getName());
    }

    @Test
    public void givenInquiryingFooWithName_whenFooComponentIsMocked_thenAssertMockedResult() {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("name", "Foo_Name");
        ResponseEntity<Foo> fooResponse = testRestTemplate.getForEntity("/?name={name}", Foo.class, pathVariables);

        assertNotNull(fooResponse);
        assertEquals(HttpStatus.OK, fooResponse.getStatusCode());
        assertEquals(1, fooResponse.getBody().getId().longValue());
    }
}