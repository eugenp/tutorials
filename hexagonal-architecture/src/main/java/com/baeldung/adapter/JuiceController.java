package com.baeldung.adapter;

import com.baeldung.domain.Juice;
import com.baeldung.port.inbound.JuiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/juice")
public class JuiceController {

    @Autowired
    private JuiceService juiceService;

    @PostMapping
    public void createJuice(@RequestBody Juice juice) {
        juiceService.createJuice(juice);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Juice> getJuice(@PathVariable String name) {
        return new ResponseEntity<>(juiceService.retrieveJuice(name), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Juice>> listJuices() {
        return new ResponseEntity<>(juiceService.listJuices(), HttpStatus.OK);
    }

}
