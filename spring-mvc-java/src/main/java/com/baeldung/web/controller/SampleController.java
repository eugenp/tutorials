package com.baeldung.web.controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SampleController {

    @GetMapping("/sample")
    public String showForm() {
        return "sample";
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

    @GetMapping("/age")
    public ResponseEntity<String> age(@RequestParam("yearOfBirth") int yearOfBirth) {

        if (isInFuture(yearOfBirth)) {
            return new ResponseEntity<>(
                    "Year of birth cannot be in the future", 
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                "Your age is " + calculateAge(yearOfBirth), 
                HttpStatus.OK);
    }

    @GetMapping("/customHeader")
    public ResponseEntity<String> customHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        return new ResponseEntity<>(
                "Custom header set", headers, HttpStatus.OK);
    }
    
    @GetMapping("/manual")
    public void manual(HttpServletResponse response) throws IOException {
        response.setHeader("Custom-Header", "foo");
        response.setStatus(200);
        response.getWriter().println("Hello World!");
    }

    private int calculateAge(int yearOfBirth) {
        return Calendar.getInstance().get(Calendar.YEAR) - yearOfBirth;
    }

    private boolean isInFuture(int yearOfBirth) {
        return yearOfBirth > Calendar.getInstance().get(Calendar.YEAR);
    }
}
