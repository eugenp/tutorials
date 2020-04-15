package com.baeldung.spring.headers.controller;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadHeaderRestController {
    private static final Log LOG = LogFactory.getLog(ReadHeaderRestController.class);
    
    @GetMapping("/")
    public ResponseEntity<String> index() {
        return new ResponseEntity<String>("Index", HttpStatus.OK);
    }

    @GetMapping("/greeting")
    public ResponseEntity<String> greeting(@RequestHeader(value = "accept-language") String language) {
        String greeting = "";
        String firstLanguage = (language.length() > 1 ? language.substring(0, 2) : language);
        switch (firstLanguage) {
        case "es":
            greeting = "Hola!";
            break;
        case "de":
            greeting = "Hallo!";
            break;
        case "fr":
            greeting = "Bonjour!";
            break;
        case "en":
        default:
            greeting = "Hello!";
            break;
        }

        return new ResponseEntity<String>(greeting, HttpStatus.OK);
    }

    @GetMapping("/double")
    public ResponseEntity<String> doubleNumber(@RequestHeader("my-number") int myNumber) {
        return new ResponseEntity<String>(
            String.format("%d * 2 = %d", myNumber, (myNumber * 2)), 
            HttpStatus.OK);
    }

    @GetMapping("/listHeaders")
    public ResponseEntity<String> listAllHeaders(@RequestHeader Map<String, String> headers) {

        headers.forEach((key, value) -> {
            LOG.info(String.format("Header '%s' = %s", key, value));
        });

        return new ResponseEntity<String>(String.format("Listed %d headers", headers.size()), HttpStatus.OK);
    }
    
    @GetMapping("/multiValue")
    public ResponseEntity<String> multiValue(@RequestHeader MultiValueMap<String, String> headers) {
        headers.forEach((key, value) -> {
            LOG.info(String.format("Header '%s' = %s", key, value.stream().collect(Collectors.joining("|"))));
        });
        
        return new ResponseEntity<String>(String.format("Listed %d headers", headers.size()), HttpStatus.OK);
    }

    @GetMapping("/getBaseUrl")
    public ResponseEntity<String> getBaseUrl(@RequestHeader HttpHeaders headers) {
        InetSocketAddress host = headers.getHost();
        String url = "http://" + host.getHostName() + ":" + host.getPort();
        return new ResponseEntity<String>(String.format("Base URL = %s", url), HttpStatus.OK);
    }

    @GetMapping("/nonRequiredHeader")
    public ResponseEntity<String> evaluateNonRequiredHeader(
        @RequestHeader(value = "optional-header", required = false) String optionalHeader) {

        return new ResponseEntity<String>(
            String.format("Was the optional header present? %s!", (optionalHeader == null ? "No" : "Yes")), 
            HttpStatus.OK);
    }

    @GetMapping("/default")
    public ResponseEntity<String> evaluateDefaultHeaderValue(
        @RequestHeader(value = "optional-header", defaultValue = "3600") int optionalHeader) {

        return new ResponseEntity<String>(String.format("Optional Header is %d", optionalHeader), HttpStatus.OK);
    }
}
