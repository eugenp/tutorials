package com.baeldung.web;

import com.baeldung.custom.persistence.model.Foo;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;


public class ApplicationLiveTest {
    
    @Test
    public void givenUserWithReadPrivilegeAndHasPermission_whenGetFooById_thenOK() {
        final Response response = givenAuth("john", "123").get("http://localhost:8082/foos/1");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(response.asString().contains("id"));
    }

    @Test
    public void givenUserWithNoWritePrivilegeAndHasPermission_whenPostFoo_thenForbidden() {
        final Response response = givenAuth("john", "123").contentType(MediaType.APPLICATION_JSON_VALUE).body(new Foo("sample")).post("http://localhost:8082/foos");
        Assert.assertEquals(403, response.getStatusCode());
    }

    @Test
    public void givenUserWithWritePrivilegeAndHasPermission_whenPostFoo_thenOk() {
        final Response response = givenAuth("tom", "111").and().body(new Foo("sample")).and().contentType(MediaType.APPLICATION_JSON_VALUE).post("http://localhost:8082/foos");
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertTrue(response.asString().contains("id"));
    }

    //

    @Test
    public void givenUserMemberInOrganization_whenGetOrganization_thenOK() {
        final Response response = givenAuth("john", "123").get("http://localhost:8082/organizations/1");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(response.asString().contains("id"));
    }

    @Test
    public void givenUserMemberNotInOrganization_whenGetOrganization_thenForbidden() {
        final Response response = givenAuth("john", "123").get("http://localhost:8082/organizations/2");
        Assert.assertEquals(403, response.getStatusCode());
    }

    //

    @Test
    public void givenDisabledSecurityExpression_whenGetFooByName_thenError() {
        final Response response = givenAuth("john", "123").get("http://localhost:8082/foos?name=sample");
        Assert.assertEquals(500, response.getStatusCode());
        Assert.assertTrue(response.asString().contains("method hasAuthority() not allowed"));
    }

    //
    private RequestSpecification givenAuth(String username, String password) {
        return RestAssured.given().log().uri().auth().form(username, password, new FormAuthConfig("/login","username","password"));
    }
}