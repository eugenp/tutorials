package org.java.spring.hexagonalarchitecture.outside;

import java.util.List;

import org.java.spring.hexagonalarchitecture.inside.Person;
import org.java.spring.hexagonalarchitecture.inside.PersonControllerPort;
import org.java.spring.hexagonalarchitecture.inside.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonRestControllerAdapter implements PersonControllerPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRestControllerAdapter.class);
    @Autowired
    PersonService personService;

    @GetMapping("/")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping("/")
    public Person save(@RequestBody Person person) {
        return personService.save(person);
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

}
