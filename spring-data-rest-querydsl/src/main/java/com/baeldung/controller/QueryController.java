package com.baeldung.controller;

import com.baeldung.controller.repository.PersonRepository;
import com.baeldung.entity.Person;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController public class QueryController {

    @Autowired private PersonRepository personRepository;

    @GetMapping(value = "/personQuery", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Person> getFilteredEvents(@QuerydslPredicate(root = Person.class) Predicate predicate) {
        final BooleanBuilder builder = new BooleanBuilder();
        return personRepository.findAll(builder.and(predicate));
    }
}
