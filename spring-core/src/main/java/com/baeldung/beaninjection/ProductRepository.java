package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductRepository {

    private DbConnection dbConnection;

    public ProductRepository() {
    }

    @Autowired
    public ProductRepository(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
