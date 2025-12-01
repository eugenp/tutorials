package com.example.Controller;

import com.example.Model.Person;
import com.example.Model.PersonDto;
import com.example.Service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/test-mapper")
    public PersonDto testMapper() {
        Person person = new Person("Alice", 25);
        return personService.convertToDto(person);
    }
}


