package com.baeldung.requestmapping;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.web.dto.Bazz;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/bazz")
public class BazzNewMappingsExampleController {

    @GetMapping
    public ResponseEntity<?> getBazzs() throws JsonProcessingException{
        List<Bazz> data = Arrays.asList(
            new Bazz("1", "Bazz1"),
            new Bazz("2", "Bazz2"),
            new Bazz("3", "Bazz3"),
            new Bazz("4", "Bazz4"));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getBazz(@PathVariable String id){
        return new ResponseEntity<>(new Bazz(id, "Bazz"+id), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<?> newBazz(@RequestParam("name") String name){
        return new ResponseEntity<>(new Bazz("5", name), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBazz(@PathVariable String id,
        @RequestParam("name") String name){
        return new ResponseEntity<>(new Bazz(id, name), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBazz(@PathVariable String id){
        return new ResponseEntity<>(new Bazz(id), HttpStatus.OK);
    }
    
}
