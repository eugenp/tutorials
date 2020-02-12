package org.baeldung.boot.converter.controller;

import org.baeldung.boot.domain.Bar;
import org.baeldung.boot.domain.Foo;
import org.baeldung.boot.domain.Modes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/string-to-abstract")
public class AbstractEntityController {

    @GetMapping("/foo/{foo}")
    public ResponseEntity<Object> getStringToFoo(@PathVariable Foo foo) {
        return ResponseEntity.ok(foo);
    }
    
    @GetMapping("/bar/{bar}")
    public ResponseEntity<Object> getStringToBar(@PathVariable Bar bar) {
        return ResponseEntity.ok(bar);
    }
    
    @GetMapping
    public ResponseEntity<Object> getStringToMode(@RequestParam("mode") Modes mode) {
        return ResponseEntity.ok(mode);
    }
}
