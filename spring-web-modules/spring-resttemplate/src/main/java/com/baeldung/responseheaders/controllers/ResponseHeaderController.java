package com.baeldung.responseheaders.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/single-response-header")
public class ResponseHeaderController {

    @GetMapping("/http-servlet-response")
    public String usingHttpServletResponse(HttpServletResponse response) {
        response.addHeader("Baeldung-Example-Header", "Value-HttpServletResponse");
        return "Response with header using HttpServletResponse";
    }

    @GetMapping("/response-entity-constructor")
    public ResponseEntity<String> usingResponseEntityConstructor() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Baeldung-Example-Header", "Value-ResponseEntityContructor");
        String responseBody = "Response with header using ResponseEntity (constructor)";
        HttpStatus responseStatus = HttpStatus.OK;

        return new ResponseEntity<String>(responseBody, responseHeaders, responseStatus);
    }

    @GetMapping("/response-entity-contructor-multiple-headers")
    public ResponseEntity<String> usingResponseEntityConstructorAndMultipleHeaders() {
        List<MediaType> acceptableMediaTypes = new ArrayList<>(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Baeldung-Example-Header", "Value-ResponseEntityConstructorAndHeaders");
        responseHeaders.setAccept(acceptableMediaTypes);
        String responseBody = "Response with header using ResponseEntity (constructor)";
        HttpStatus responseStatus = HttpStatus.OK;

        return new ResponseEntity<String>(responseBody, responseHeaders, responseStatus);
    }

    @GetMapping("/response-entity-builder")
    public ResponseEntity<String> usingResponseEntityBuilder() {
        String responseHeaderKey = "Baeldung-Example-Header";
        String responseHeaderValue = "Value-ResponseEntityBuilder";
        String responseBody = "Response with header using ResponseEntity (builder)";

        return ResponseEntity.ok()
            .header(responseHeaderKey, responseHeaderValue)
            .body(responseBody);
    }

    @GetMapping("/response-entity-builder-with-http-headers")
    public ResponseEntity<String> usingResponseEntityBuilderAndHttpHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Baeldung-Example-Header", "Value-ResponseEntityBuilderWithHttpHeaders");
        String responseBody = "Response with header using ResponseEntity (builder)";

        return ResponseEntity.ok()
            .headers(responseHeaders)
            .body(responseBody);
    }
}
