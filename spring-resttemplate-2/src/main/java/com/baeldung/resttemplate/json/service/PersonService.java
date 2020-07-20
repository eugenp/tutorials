package com.baeldung.resttemplate.json.service;


import com.baeldung.resttemplate.json.Person;

public interface PersonService {

    public Person saveUpdatePerson(Person person);

    public Person findPersonById(Integer id);
}