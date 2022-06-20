package com.baeldung.pact;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.baeldung.pact.config.MainApplication;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;

@Provider("test_provider")
@PactFolder("target/pacts")
public class PactProviderLiveTest {
	
    private static ConfigurableWebApplicationContext application;

    @BeforeAll
    public static void start() {
        application = (ConfigurableWebApplicationContext) SpringApplication.run(MainApplication.class);
    }
    
    @BeforeEach
    void before(PactVerificationContext context) {
    	   context.setTarget(new HttpTestTarget("localhost", 8082, "/spring-rest"));
    	}
    
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
       context.verifyInteraction();
    }

    @State("test GET")
    public void toGetState() {
    }

    @State("test POST")
    public void toPostState() {
    }

}
