package com.baeldung.controller.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingsController {
    
    @RequestMapping(
        value = "/greetings-with-response-body", 
        method = RequestMethod.GET, 
        produces="application/json"
    ) 
    @ResponseBody
    public String getGreetingWhileReturnTypeIsString() { 
        return "{\"test\": \"Hello using @ResponseBody\"}";
    }
        
    @RequestMapping(
        value = "/greetings-with-response-entity",
        method = RequestMethod.GET, 
        produces = "application/json"
    )
    public ResponseEntity<String> getGreetingWithResponseEntity() {
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>("{\"test\": \"Hello with ResponseEntity\"}", httpHeaders, HttpStatus.OK);
    }
    @RequestMapping(
        value = "/greetings-with-map-return-type", 
        method = RequestMethod.GET, 
        produces = "application/json"
    )
    @ResponseBody
    public Map<String, Object> getGreetingWhileReturnTypeIsMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("test", "Hello from map");
        return map;
    }
}