package com.baeldung.dependencyinjectiontypes;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Intends to show a field Spring DI.
 */
@Component
public class FooFieldDI {
    @Autowired private Repository mariaDB;


    public Repository getMariaDB() {
        return mariaDB;
    }
}
