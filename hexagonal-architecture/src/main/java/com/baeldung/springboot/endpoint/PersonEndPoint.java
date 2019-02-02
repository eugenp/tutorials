package com.baeldung.springboot.endpoint;

import com.baeldung.springboot.model.PersonResponse;
import com.baeldung.springboot.model.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/persons")
public interface PersonEndPoint {

    @PostMapping
    ResponseEntity<PersonResponse> registerPerson(@RequestBody PersonDto personDto);

}
