package com.baeldung.jsonb;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/person")
public class PersonController {

    List<Person> personRepository;

    @PostConstruct
    public void init() {
        // @formatter:off
        personRepository = new ArrayList<>(Arrays.asList(
            new Person(1, "Jhon", "jhon@test.com", 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1000)), 
            new Person(2, "Jhon", "jhon1@test.com", 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1500)), 
            new Person(3, "Jhon", null, 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1000)),  
            new Person(4, "Tom", "tom@test.com", 21, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1500)), 
            new Person(5, "Mark", "mark@test.com", 21, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1200)), 
            new Person(6, "Julia", "jhon@test.com", 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1000))));
        // @formatter:on

    }

    @GetMapping("/person/{id}")
    @ResponseBody
    public Person findById(@PathVariable final int id) {
        return personRepository.get(id);
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean insertPerson(@RequestBody final Person person) {
        return personRepository.add(person);
    }

    @GetMapping("/person")
    @ResponseBody
    public List<Person> findAll() {
        return personRepository;
    }

}
