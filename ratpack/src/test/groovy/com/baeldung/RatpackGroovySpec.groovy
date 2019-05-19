package com.baeldung;

import ratpack.groovy.Groovy
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest;
import ratpack.test.http.TestHttpClient;
import ratpack.test.ServerBackedApplicationUnderTest;
import org.junit.Test;
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ratpack.test.MainClassApplicationUnderTest

class RatpackGroovySpec {

    ServerBackedApplicationUnderTest aut = new MainClassApplicationUnderTest(RatpackGroovyApp.class)
    @Delegate TestHttpClient client = TestHttpClient.testHttpClient(aut)

    @Test
    void "test if app is started"() {
        when:
        get("")

        then:
        response.statusCode == 200
        assert response.body.text == "Hello World from Ratpck with Groovy!!"
    }
    
    @Test
    void "test greet with name"() {
        when:
        get("greet/Lewis")
        
        then:
        response.statusCode == 200
        assert response.body.text == "Hello Lewis !!!"
    }
    
    @Test
    void "test fetchUsers"() {
        when:
        get("fetchUsers")
        
        then:
        response.statusCode == 200
        response.body.text == '[{"ID":1,"TITLE":"Mr.","NAME":"Norman","COUNTRY":"USA"},{"ID":2,"TITLE":"Mr.","NAME":"Lewis","COUNTRY":"USA"}]'
    }
    
}
