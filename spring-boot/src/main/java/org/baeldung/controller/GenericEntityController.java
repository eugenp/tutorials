package org.baeldung.controller;

import org.baeldung.domain.GenericEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
