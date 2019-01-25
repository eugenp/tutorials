package com.baeldung.adapter;


import com.baeldung.contract.DatabaseService;
import com.baeldung.model.Student;

public class MySqlAdapter implements DatabaseService {

    public void save(Student student) {
        // connect to db
        // save record in db
        System.out.println("save a record into mysql database");
    }

    public void update(Student student) {
        // connect to db
        // update record in db
        System.out.println("update a record in mysql database");
    }

    public void findById(int id) {
        // connect to db
        // find a record from db
        System.out.println("find a record from mysql database");
    }
}
