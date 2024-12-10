package com.baeldung.chaindofilter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chain/do-filter")
public class ChainDoFilterController {

    public static final String RESPONSE_MESSAGE = "Chain doFilter test!";

    @GetMapping
    public ResponseEntity<String> chainDoFilter() {
        return ResponseEntity.ok(RESPONSE_MESSAGE);
    }

}
