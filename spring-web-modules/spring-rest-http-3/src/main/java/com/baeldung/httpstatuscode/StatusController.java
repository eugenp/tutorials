package com.baeldung.httpstatuscode;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.baeldung.httpstatuscode.exception.CustomException;

@Controller
public class StatusController {

    @GetMapping("/resource")
    public ResponseEntity<String> successStatusCode() {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(200);
        if (statusCode.is2xxSuccessful()) {
            return new ResponseEntity<>("Success", statusCode);
        }

        return new ResponseEntity<>("Moved Permanently", HttpStatusCode.valueOf(301));
    }

    @GetMapping("/exception")
    public ResponseEntity<String> resourceNotFound() {
        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        if (statusCode.is4xxClientError()) {
            return new ResponseEntity<>("Resource not found", HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>("Resource found", HttpStatusCode.valueOf(200));
    }

    @GetMapping("/custom-exception")
    public ResponseEntity<String> goneStatusCode() {
        throw new CustomException("Resource Gone", HttpStatusCode.valueOf(410));
    }
}
