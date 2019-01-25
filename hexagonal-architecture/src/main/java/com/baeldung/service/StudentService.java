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

        // Adapter
        DatabaseService databaseService = new MySqlAdapter();

        // business service
        StudentService studentService = new StudentService(databaseService);

        studentService.saveStudentInfo();


        // Adapter
        DatabaseService databaseServiceForOracle = new OracleServerAdapter();

        // business service
        StudentService studentServiceV2 = new StudentService(databaseServiceForOracle);

        studentServiceV2.saveStudentInfo();
    }


}
