package com.baeldung.singleton;

import lombok.Getter;


public class GetterLazy {

    @Getter(lazy = true)
    private final String name = "name";
}
