package com.baeldung.springboot.rest.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class ApplicationClient {
    final static String URI_STRING = "http://localhost:8989/DifferenceURIURLREST/greetings";

    public ApplicationClient() {
        super();
    }
    
    public Greeting init() {
        RestTemplate restTemplate = new RestTemplate();
        Greeting greeting = restTemplate.getForObject(ApplicationClient.URI_STRING, Greeting.class);
        return greeting;
    }
        public static void main(String args[]) {
            Greeting greeting = new ApplicationClient().init();
            System.out.println(greeting.toString());
        }
}
