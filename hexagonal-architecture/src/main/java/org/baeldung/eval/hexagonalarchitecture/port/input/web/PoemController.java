package org.baeldung.eval.hexagonalarchitecture.port.input.web;

import java.util.List;
import java.util.UUID;

import org.baeldung.eval.hexagonalarchitecture.data.Poem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface PoemController {
    
    @PostMapping("/poems")
    ResponseEntity<Void> addPoems(@RequestBody Poem pomeDTO);

    @DeleteMapping("/poems")
    ResponseEntity<String> removePoems(@RequestBody Poem pomeDTO);

    @PutMapping("/poems")
    ResponseEntity<String> updatePoems(@RequestBody Poem pomeDTO);

    @GetMapping("/poems/{pomeId}")
    ResponseEntity<Poem> getPoemsById(@PathVariable(name = "pomeId") UUID poemId);

    @GetMapping("/poems")
    ResponseEntity<List<Poem>> poems();

}