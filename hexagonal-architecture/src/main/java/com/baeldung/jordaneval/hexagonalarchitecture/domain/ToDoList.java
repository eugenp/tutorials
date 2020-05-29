package com.baeldung.jordaneval.hexagonalarchitecture.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ToDoList {
    private UUID id;
    private String contents;
}
