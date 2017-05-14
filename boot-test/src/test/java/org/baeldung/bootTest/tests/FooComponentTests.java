package org.baeldung.bootTest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;

import java.util.HashMap;
import java.util.Map;

import org.baeldung.bootTest.components.FooComponent;
import org.baeldung.bootTest.model.Foo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FooComponentTests {

    @Autowired
    private TestRestTemplate testRestTemplate;
    
    @MockBean
    private FooComponent fooComponent;
    
    @Before
    public void init(){
        Foo foo = new Foo();
        foo.setId(5l);
        foo.setName("MOCKED_FOO");
        
        doReturn(foo).when(fooComponent).getFooWithId(anyLong());
        
        doCallRealMethod().when(fooComponent).getFooWithName(anyString());
    }
    
    
    @Test
    public void givenInquiryingFooWithId_whenFooComponentIsMocked_thenAssertMockedResult(){
        Map<String,String> pathVariables = new HashMap<String,String>();
        pathVariables.put("id", "1");
        ResponseEntity<Foo> fooResponse = testRestTemplate.getForEntity("/{id}", Foo.class, pathVariables);
        
        assertNotNull(fooResponse);
        assertEquals(HttpStatus.OK,fooResponse.getStatusCode());
        assertEquals(5,fooResponse.getBody().getId().longValue());
        assertEquals("MOCKED_FOO",fooResponse.getBody().getName());
    }
    
    @Test
    public void givenInquiryingFooWithName_whenFooComponentIsMocked_thenAssertMockedResult(){
        Map<String,String> pathVariables = new HashMap<String,String>();
        pathVariables.put("name", "Foo_Name");
        ResponseEntity<Foo> fooResponse = testRestTemplate.getForEntity("/?name={name}", Foo.class, pathVariables);
        
        assertNotNull(fooResponse);
        assertEquals(HttpStatus.OK,fooResponse.getStatusCode());
        assertEquals(1,fooResponse.getBody().getId().longValue());
    }
}