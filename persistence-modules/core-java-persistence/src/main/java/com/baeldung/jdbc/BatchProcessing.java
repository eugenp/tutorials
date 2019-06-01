package com.baeldung.jdbc;

import java.sql.*;
import java.util.UUID;

public class BatchProcessing {

    private final String[] EMPLOYEES = new String[]{"Zuck","Mike","Larry","Musk","Steve"};
    private final String[] DESIGNATIONS = new String[]{"CFO","CSO","CTO","CEO","CMO"};
    private final String[] ADDRESSES = new String[]{"China","York","Diego","Carolina","India"};

    private Connection connection;

    public void getConnection(){
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:mem:db", "SA", "");
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void createTables(){
        try {
            connection.createStatement().executeUpdate("create table EMPLOYEE (ID VARCHAR(36), NAME VARCHAR(45), DESIGNATION VARCHAR(15))");
            connection.createStatement().executeUpdate("create table EMP_ADDRESS (ID VARCHAR(36), EMP_ID VARCHAR(36), ADDRESS VARCHAR(45))");
            System.out.println("Tables Created!!!");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public void useStatement(){
        try {
            String insertEmployeeSQL = "INSERT INTO EMPLOYEE(ID, NAME, DESIGNATION) VALUES ('%s','%s','%s');";
            String insertEmployeeAddrSQL = "INSERT INTO EMP_ADDRESS(ID, EMP_ID, ADDRESS) VALUES ('%s','%s','%s');";
            Statement statement = connection.createStatement();
            for(int i = 0; i < EMPLOYEES.length; i++){
                String employeeId = UUID.randomUUID().toString();
                statement.addBatch(String.format(insertEmployeeSQL, employeeId, EMPLOYEES[i],DESIGNATIONS[i]));
                statement.addBatch(String.format(insertEmployeeAddrSQL, UUID.randomUUID().toString(),employeeId,ADDRESSES[i]));
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error during rollback");
                System.out.println(ex.getMessage());
            }
            e.printStackTrace(System.out);
        }
    }

    public void usePreparedStatement(){
        try {
            String insertEmployeeSQL = "INSERT INTO EMPLOYEE(ID, NAME, DESIGNATION) VALUES (?,?,?);";
            String insertEmployeeAddrSQL = "INSERT INTO EMP_ADDRESS(ID, EMP_ID, ADDRESS) VALUES (?,?,?);";
            PreparedStatement employeeStmt = connection.prepareStatement(insertEmployeeSQL);
            PreparedStatement empAddressStmt = connection.prepareStatement(insertEmployeeAddrSQL);
            for(int i = 0; i < EMPLOYEES.length; i++){
                String employeeId = UUID.randomUUID().toString();
                employeeStmt.setString(1,employeeId);
                employeeStmt.setString(2,EMPLOYEES[i]);
                employeeStmt.setString(3,DESIGNATIONS[i]);
                employeeStmt.addBatch();

                empAddressStmt.setString(1,UUID.randomUUID().toString());
                empAddressStmt.setString(2,employeeId);
                empAddressStmt.setString(3,ADDRESSES[i]);
                empAddressStmt.addBatch();
            }
            employeeStmt.executeBatch();
            empAddressStmt.executeBatch();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error during rollback");
                System.out.println(ex.getMessage());
            }
            e.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) {
        BatchProcessing batchProcessing = new BatchProcessing();
        batchProcessing.getConnection();
        batchProcessing.createTables();
        batchProcessing.useStatement();
        batchProcessing.usePreparedStatement();
    }
}
