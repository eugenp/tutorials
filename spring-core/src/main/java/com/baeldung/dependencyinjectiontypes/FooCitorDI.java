package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * Intends to show an example of constructor injection of Spring.
 */
@Component
public class FooCitorDI {
    private Repository mariaDB;

    @Autowired
    FooCitorDI(Repository db) {
        this.mariaDB = db;
    }

    public Repository getMariaDB() {
        return mariaDB;
    }
}
