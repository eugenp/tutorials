package com.baeldung.boot.controller;

import com.baeldung.boot.domain.GenericEntity;
import com.baeldung.boot.domain.Modes;
import com.baeldung.boot.web.resolver.Version;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GenericEntityController {
    private List<GenericEntity> entityList = new ArrayList<>();

    {
        entityList.add(new GenericEntity(1l, "entity_1"));
        entityList.add(new GenericEntity(2l, "entity_2"));
        entityList.add(new GenericEntity(3l, "entity_3"));
        entityList.add(new GenericEntity(4l, "entity_4"));
    }

    @GetMapping("/entity/all")
    public List<GenericEntity> findAll() {
        return entityList;
    }

    @PostMapping("/entity")
    public GenericEntity addEntity(GenericEntity entity) {
        entityList.add(entity);
        return entity;
    }

    @GetMapping("/entity/findby/{id}")
    public GenericEntity findById(@PathVariable Long id) {
        return entityList.stream().filter(entity -> entity.getId().equals(id)).findFirst().get();
    }

    @GetMapping("/entity/findbydate/{date}")
    public GenericEntity findByDate(@PathVariable("date") LocalDateTime date) {
        return entityList.stream().findFirst().get();
    }

    @GetMapping("/entity/findbymode/{mode}")
    public GenericEntity findByEnum(@PathVariable("mode") Modes mode) {
        return entityList.stream().findFirst().get();
    }

    @GetMapping("/entity/findbyversion")
    public ResponseEntity findByVersion(@Version String version) {
        return version != null ? new ResponseEntity(entityList.stream().findFirst().get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
