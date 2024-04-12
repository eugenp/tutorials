package com.baeldung.serializeentityid;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.springframework.data.rest.core.config.Projection;

@JsonPropertyOrder({ "id", "name" })
@Projection(name = "person-view", types = Person.class)
public interface PersonView {

    Long getId();

    String getName();

}
