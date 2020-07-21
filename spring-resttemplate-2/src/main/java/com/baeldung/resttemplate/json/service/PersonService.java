package com.baeldung.resttemplate.json.service;


import com.baeldung.resttemplate.json.dto.Person;

public interface PersonService {

    public Person saveUpdatePerson(Person person);

    public Person findPersonById(Integer id);
}