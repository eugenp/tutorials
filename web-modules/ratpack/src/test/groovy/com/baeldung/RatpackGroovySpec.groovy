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

    ServerBackedApplicationUnderTest ratpackGroovyApp = new MainClassApplicationUnderTest(RatpackGroovyApp.class)
    @Delegate TestHttpClient client = TestHttpClient.testHttpClient(ratpackGroovyApp)

    @Test
    void "test if app is started"() {
        when:
        get("")

        then:
        assert response.statusCode == 200
        assert response.body.text == "Hello World from Ratpack with Groovy!!"
    }

    @Test
    void "test greet with name"() {
        when:
        get("greet/Lewis")

        then:
        assert response.statusCode == 200
        assert response.body.text == "Hello Lewis!!!"
    }

    @Test
    void "test fetchUsers"() {
        when:
        get("fetchUsers")

        then:
        assert response.statusCode == 200
        assert response.body.text == '[{"ID":1,"TITLE":"Mr","NAME":"Norman Potter","COUNTRY":"USA"},{"ID":2,"TITLE":"Miss","NAME":"Ketty Smith","COUNTRY":"FRANCE"}]'
    }
}
