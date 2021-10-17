package com.baeldung.architecture.hexagonal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Issue {
    private Long id;
    private String type;
    private String description;
}
