package com.baeldung.service;

import com.baeldung.adapter.MySqlAdapter;
import com.baeldung.adapter.OracleServerAdapter;
import com.baeldung.contract.DatabaseService;
import com.baeldung.model.Student;


public class StudentService {

    DatabaseService databaseService;

    public StudentService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void saveStudentInfo() {
        Student student = new Student();
        //build student information
        databaseService.save(student);
    }

    public static void main(String[] args) {

        // MySql Adapter
        DatabaseService databaseServiceV1 = new MySqlAdapter();
        // business service with MySql Adapte
        StudentService studentServiceForMySql = new StudentService(databaseServiceV1);
        studentServiceForMySql.saveStudentInfo();


        // Oracle Adapter
        DatabaseService databaseServiceV2 = new OracleServerAdapter();
        // business service with Oracle Adapter
        StudentService studentServiceV2 = new StudentService(databaseServiceV2);
        studentServiceV2.saveStudentInfo();
    }
}
