package com.baeldung.adapter;


import com.baeldung.contract.DatabaseService;
import com.baeldung.model.Student;

public class OracleServerAdapter implements DatabaseService {

    public void save(Student student) {
        // connect to oracle db
        // save a record in oracle db
        System.out.println("save a record into oracle server database");
    }

    public void update(Student student) {

        // connect to oracle db
        // update a record in oracle db
        System.out.println("update a record in oracle server database");
    }

    public void findById(int id) {

        // connect to oracle db
        // find a record from oracle db
        System.out.println("find a record from oracle server database");
    }
}
