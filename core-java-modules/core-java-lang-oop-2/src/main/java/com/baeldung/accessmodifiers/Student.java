package com.baeldung.accessmodifiers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Student {

    private BigDecimal grades; //new representation
    private String name;   
    private int age;

    public int getGrades() {
        return grades.intValue(); //Backward compatibility
    }
    
    public Connection getConnection() throws SQLException {
        
        final String URL = "jdbc:h2:~/test";
        return DriverManager.getConnection(URL, "sa", "");

    }
    
    public BigDecimal bigDecimalGrades() {
        return grades;
    }
        
    public void setAge(int age) {
        if (age < 0 || age > 150)
            throw new IllegalArgumentException();

        this.age = age;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
