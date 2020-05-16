package com.baeldung.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestMappingShortcutsController {

    @GetMapping("/get")
    public @ResponseBody ResponseEntity<String> get() {
        return new ResponseEntity<String>("GET Response", HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public @ResponseBody ResponseEntity<String> getById(@PathVariable String id) {
        return new ResponseEntity<String>("GET Response : " + id, HttpStatus.OK);
    }

    @PostMapping("/post")
    public @ResponseBody ResponseEntity<String> post() {
        return new ResponseEntity<String>("POST Response", HttpStatus.OK);
    }

    @PutMapping("/put")
    public @ResponseBody ResponseEntity<String> put() {
        return new ResponseEntity<String>("PUT Response", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<String> delete() {
        return new ResponseEntity<String>("DELETE Response", HttpStatus.OK);
    }

    @PatchMapping("/patch")
    public @ResponseBody ResponseEntity<String> patch() {
        return new ResponseEntity<String>("PATCH Response", HttpStatus.OK);
    }

}
