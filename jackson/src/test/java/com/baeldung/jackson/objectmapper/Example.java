package com.baeldung.jackson.objectmapper;

import org.junit.Test;

public abstract class Example {

    String EXAMPLE_JSON = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";

    public abstract String name();

    public abstract void execute();

    @Test
    public abstract void test() throws Exception;
}
