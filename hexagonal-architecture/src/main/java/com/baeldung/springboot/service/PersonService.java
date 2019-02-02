package com.baeldung.springboot.service;

import com.baeldung.springboot.model.PersonResponse;
import com.baeldung.springboot.model.dto.PersonDto;

public interface PersonService {

    PersonResponse createPerson(PersonDto personDto);

}
