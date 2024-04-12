package com.baeldung.lombok.jackson;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class Fruit {

    private String name;
    private int id;

}
