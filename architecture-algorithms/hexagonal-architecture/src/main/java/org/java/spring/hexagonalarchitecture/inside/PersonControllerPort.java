package org.java.spring.hexagonalarchitecture.inside;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface PersonControllerPort {

    public List<Person> findAll();

    public Person save(Person person);

    public Person findById(Long id);

}
