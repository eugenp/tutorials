package com.baeldung.architecture.hexagonal.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Getter
@ToString
@AllArgsConstructor
public class Quote {

    private ObjectId id;
    private String quote;
    private String author;

}
