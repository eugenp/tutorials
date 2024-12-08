package com.baeldung.chaindofilter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chain/do-filter")
public class ChainDoFilterController {

    @GetMapping
    public ResponseEntity<String> chainDoFilter() {
        return ResponseEntity.ok("Chain doFilter test!");
    }

}
