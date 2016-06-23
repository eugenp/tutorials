package com.baeldung;

import cucumber.api.java.en.Given;


public class OtherDefs extends AbstractDefs{
//    @When("^the client calls /poop$")
//    public void the_client_issues_GET_version() throws Throwable{
//        executeGet("http://localhost:8080/poop");
//    }
    
    @Given("^the client calls /hello$")
    public void the_client_issues_GET_hello() throws Throwable{
        executeGet("http://localhost:8080/hello");
    }
}