package com.baeldung.tls;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/baeldung")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("tls/baeldung", HttpStatus.OK);
    }

}
