package com.baeldung.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class TodosApplication {

    public static void main(String[] args) {
        Quarkus.run(args);
    }

}
