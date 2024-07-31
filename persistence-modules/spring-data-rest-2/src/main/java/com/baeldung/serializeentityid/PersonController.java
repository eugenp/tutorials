package com.baeldung.serializeentityid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RepositoryRestController
public class PersonController {

    @Autowired
    PersonRepository repository;

    @GetMapping("/persons")
    ResponseEntity<?> persons(PagedResourcesAssembler resourcesAssembler) {
        Page<Person> persons = this.repository.findAll(Pageable.ofSize(20));
        Page<PersonDto> personDtos = persons.map(PersonDto::new);
        PagedModel<EntityModel<PersonDto>> pagedModel = resourcesAssembler.toModel(personDtos);
        return ResponseEntity.ok(pagedModel);
    }

}
