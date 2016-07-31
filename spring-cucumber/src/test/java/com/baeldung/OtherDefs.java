package com.baeldung;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;


public class OtherDefs extends SpringIntegrationTest{
    @When("^the client calls /baeldung$")
    public void the_client_issues_POST_hello() throws Throwable{
        executePost("http://localhost:8080/baeldung");
    }
    
    @Given("^the client calls /hello$")
    public void the_client_issues_GET_hello() throws Throwable{
        executeGet("http://localhost:8080/hello");
    }
}