package com.baeldung.dependency.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

class DaoFactoryWithConstructorInjection {

    private Dao databaseDao;

    private Dao fileDao;

    @Autowired
    public DaoFactoryWithConstructorInjection(Dao databaseDao, Dao fileDao) {
        this.databaseDao = databaseDao;
        this.fileDao = fileDao;
    }

    Dao create(boolean shouldUseDatabase) {
        if (shouldUseDatabase) {
            return databaseDao;
        }
        return fileDao;
    }

}
