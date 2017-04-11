package org.baeldung.controller;

import org.baeldung.domain.GenericEntity;
import org.baeldung.domain.Modes;
import org.baeldung.web.resolver.Version;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping("/entity/all")
    public List<GenericEntity> findAll() {
        return entityList;
    }

    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public GenericEntity addEntity(GenericEntity entity) {
        entityList.add(entity);
        return entity;
    }

    @RequestMapping("/entity/findby/{id}")
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
