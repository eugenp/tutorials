package com.baeldung;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Hobby {

    private String name;
    private String description;

    public Hobby(Hobby hobby) {
        this.name = hobby.name;
        this.description = hobby.description;
    }
}
