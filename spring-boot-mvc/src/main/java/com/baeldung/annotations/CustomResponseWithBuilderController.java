package com.baeldung.annotations;

import java.time.Year;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customResponseWithBuilder")
public class CustomResponseWithBuilderController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/age")
    public ResponseEntity<String> age(@RequestParam("yearOfBirth") int yearOfBirth) {
        if (isInFuture(yearOfBirth)) {
            return ResponseEntity.badRequest()
                .body("Year of birth cannot be in the future");
        }

        return ResponseEntity.status(HttpStatus.OK)
            .body("Your age is " + calculateAge(yearOfBirth));
    }

    private int calculateAge(int yearOfBirth) {
        return currentYear() - yearOfBirth;
    }

    private boolean isInFuture(int year) {
        return currentYear() < year;
    }

    private int currentYear() {
        return Year.now()
            .getValue();
    }

    @GetMapping("/customHeader")
    public ResponseEntity<String> customHeader() {
        return ResponseEntity.ok()
            .header("Custom-Header", "foo")
            .body("Custom header set");
    }

}
