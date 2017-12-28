package com.baeldung.dependency.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

class DaoFactoryWithSetterInjection {

    private Dao databaseDao;

    private Dao fileDao;

    Dao create(boolean shouldUseDatabase) {
        if (shouldUseDatabase) {
            return databaseDao;
        }
        return fileDao;
    }

    public Dao getDatabaseDao() {
        return databaseDao;
    }

    @Autowired
    public void setDatabaseDao(Dao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public Dao getFileDao() {
        return fileDao;
    }

    @Autowired
    public void setFileDao(Dao fileDao) {
        this.fileDao = fileDao;
    }

}
