package org.baeldung.eval.hexagonalarchitecture.adapter.web;

import java.util.List;
import java.util.UUID;

import org.baeldung.eval.hexagonalarchitecture.core.PoemService;
import org.baeldung.eval.hexagonalarchitecture.data.Poem;
import org.baeldung.eval.hexagonalarchitecture.excpetion.PoemNotFoundException;
import org.baeldung.eval.hexagonalarchitecture.port.input.web.PoemController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PoemControllerImpl implements PoemController{

    private final PoemService poemService;

    public PoemControllerImpl(PoemService poemService){
        this.poemService=poemService;
    }
    
    @Override
    public ResponseEntity<Void> addPoems(Poem poemDTO) {
        poemService.addPoems(poemDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> removePoems(Poem poemDTO) {
        poemService.removePoems(poemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updatePoems(Poem poemDTO) {
        poemService.updatePoems(poemDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Poem> getPoemsById(UUID poemId) {
        try{
            return new ResponseEntity<>((poemService.getPoemById(poemId)),HttpStatus.OK);
        }catch(PoemNotFoundException ex){
            log.error("ERROR | {}", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Poem>> poems() {
        return new ResponseEntity<>((poemService.getAllPoems()),HttpStatus.OK);
    }   
}
