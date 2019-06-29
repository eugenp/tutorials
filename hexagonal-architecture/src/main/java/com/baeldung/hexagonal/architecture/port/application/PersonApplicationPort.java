package com.baeldung.hexagonal.architecture.port.application;

import com.baeldung.hexagonal.architecture.domain.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PersonApplicationPort {

    @PostMapping("/addPerson")
    public void addPerson(@RequestBody Person person);

    @GetMapping("/getPerson/{personId}")
    public Person getPerson(@PathVariable int personId);
}
