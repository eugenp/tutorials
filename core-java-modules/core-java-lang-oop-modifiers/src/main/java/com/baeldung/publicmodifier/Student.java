package com.baeldung.publicmodifier;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class Student {

    private StudentGrade grade; //new data representation
//    private int grade; //old data representation
    private String name;   
    private int age;
    
    public void setGrade(int grade) {        
        this.grade = new StudentGrade(grade);
    }

    public int getGrade() {
        return this.grade.getGrade().intValue(); //int is returned for backward compatibility
    }
    
    public Connection getConnection() throws SQLException {
        
        final String URL = "jdbc:h2:~/test";
        return DriverManager.getConnection(URL, "sa", "");

    }
        
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException();
        }
        
        this.age = age;
    }

    public int getAge() {
        return age;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    private class StudentGrade {
        private BigDecimal grade = BigDecimal.ZERO;
        private Date updatedAt;
        
        public StudentGrade(int grade) {
            this.grade = new BigDecimal(grade);
            this.updatedAt = new Date();
        }
        
        public BigDecimal getGrade() {
            return grade;
        }
        
        public Date getDate() {
            return updatedAt;
        }
        
     }
    
}
