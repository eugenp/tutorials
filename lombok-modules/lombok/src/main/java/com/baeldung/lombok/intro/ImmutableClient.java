package com.baeldung.lombok.intro;

import lombok.Value;

@Value
final class ImmutableClient {

    private int id;
    private String name;

}
