package org.baeldung.web.controller.mediatypes;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = "application/vnd.baeldung.api.v1+json",
        consumes = "application/vnd.baeldung.api.v1+json")
public class CustomMediaTypeController {


    @RequestMapping(value = "/public/api/endpoint", produces = "application/vnd.baeldung.api.v1+json",
            consumes = "application/vnd.baeldung.api.v1+json")
    public ResponseEntity<String> getItem() {
        return new ResponseEntity<>("our item", HttpStatus.OK);
    }

    @RequestMapping(value = "/public/api/endpoint", produces = "application/vnd.baeldung.api.v2+json",
            consumes = "application/vnd.baeldung.api.v2+json")
    public ResponseEntity<Integer> getItemSecondVersion() {
        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
