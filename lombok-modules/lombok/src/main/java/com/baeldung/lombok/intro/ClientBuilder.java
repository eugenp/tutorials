package com.baeldung.lombok.intro;

import lombok.Builder;

class ClientBuilder {

    @Builder(builderMethodName = "builder")
    public static ImmutableClient newClient(int id, String name) {
        return new ImmutableClient(id, name);
    }
}
