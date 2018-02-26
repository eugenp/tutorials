package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Intends to show a setter Spring DI.
 */
@Component
public class FooSetterDI {
    private Repository mariaDB;


    public Repository getMariaDB() {
        return mariaDB;
    }

    @Autowired
    public void setMariaDB(Repository mariaDB) {
        this.mariaDB = mariaDB;
    }
}
