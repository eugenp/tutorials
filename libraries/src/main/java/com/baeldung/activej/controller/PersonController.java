package com.baeldung.activej.controller;

import com.baeldung.activej.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.activej.http.AsyncServlet;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promise;

public class PersonController implements AsyncServlet {
    private final PersonService personService;
    private final ObjectMapper objectMapper;

    public PersonController(PersonService personService) {
        this.personService = personService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Promise<HttpResponse> serve(HttpRequest httpRequest) {
        return personService.findAndVerifyPerson(httpRequest.getQueryParameter("name"))
          .map((p) -> HttpResponse.ok200().withJson(objectMapper.writeValueAsString(p)).build())
          .mapException(e -> e);
    }
}
