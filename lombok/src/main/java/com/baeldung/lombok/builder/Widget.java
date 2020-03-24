package com.baeldung.lombok.builder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Widget {

    private final String name;
    private final int id;

}
