package com.baeldung.responseentity;

import java.io.IOException;
import java.time.Year;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customResponse")
public class CustomResponseController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

    @GetMapping("/age")
    public ResponseEntity<String> age(@RequestParam("yearOfBirth") int yearOfBirth) {
        if (isInFuture(yearOfBirth)) {
            return new ResponseEntity<>("Year of birth cannot be in the future", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Your age is " + calculateAge(yearOfBirth), HttpStatus.OK);
    }

    private int calculateAge(int yearOfBirth) {
        return currentYear() - yearOfBirth;
    }

    private boolean isInFuture(int year) {
        return currentYear() < year;
    }

    private int currentYear() {
        return Year.now().getValue();
    }

    @GetMapping("/customHeader")
    public ResponseEntity<String> customHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");

        return new ResponseEntity<>("Custom header set", headers, HttpStatus.OK);
    }

    @GetMapping("/manual")
    public void manual(HttpServletResponse response) throws IOException {
        response.setHeader("Custom-Header", "foo");
        response.setStatus(200);
        response.getWriter()
            .println("Hello World!");
    }

}
