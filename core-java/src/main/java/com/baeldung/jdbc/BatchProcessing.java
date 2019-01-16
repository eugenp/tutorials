package com.baeldung.jdbc;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.collect.TreeBasedTable;

import java.sql.*;
import java.util.*;

/**
 * @author zn.wang
 */
public class BatchProcessing {

    private final String[] EMPLOYEES = new String[]{"Zuck","Mike","Larry","Musk","Steve"};
    private final String[] DESIGNATIONS = new String[]{"CFO","CSO","CTO","CEO","CMO"};
    private final String[] ADDRESSES = new String[]{"China","York","Diego","Carolina","India"};

    private Connection connection;

    /**
     * 获取连接
     */
    public void getConnection(){
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:mem:db", "SA", "");
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * 创建表
     */
    public void createTables(){
        try {
            connection.createStatement().executeUpdate("create table EMPLOYEE (ID VARCHAR(36), NAME VARCHAR(45), DESIGNATION VARCHAR(15))");
            connection.createStatement().executeUpdate("create table EMP_ADDRESS (ID VARCHAR(36), EMP_ID VARCHAR(36), ADDRESS VARCHAR(45))");
            System.out.println("Tables Created!!!");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Statement的使用
     */
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

        System.out.println("useStatement insert rows length is:{}"
                + " EMPLOYEES_insert_length:" + EMPLOYEES.length
                + " EMP_ADDRESS_insert_length:" + EMPLOYEES.length
        );
    }

    /**
     * PreparedStatement的使用
     */
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

        System.out.println("usePreparedStatement insert rows length is:{}"
                + " EMPLOYEES_insert_length:" + EMPLOYEES.length
                + " EMP_ADDRESS_insert_length:" + EMPLOYEES.length
        );
    }

    /**
     * EMPLOYEE表和EMP_ADDRESS表所有记录查询
     */
    public void  findTablesDataList(){
        List<Table<String, String ,String >> tableDataList = Lists.newArrayList();
        try{
            String selectEmployeeSQL = "select ID, NAME, DESIGNATION from EMPLOYEE;";
            String selectEmployeeAddressSQL = "select ID, EMP_ID, ADDRESS from EMP_ADDRESS;";
            ResultSet resultSetEmployee = connection.prepareStatement(selectEmployeeSQL).executeQuery();
            ResultSet resultSetEmployeeAddress = connection.prepareStatement(selectEmployeeAddressSQL).executeQuery();
            Table<String, String ,String > employeeTable = TreeBasedTable.create();
            while (resultSetEmployee.next()){
                employeeTable.put(
                        resultSetEmployee.getString("ID") ,
                        resultSetEmployee.getString("NAME"),
                        resultSetEmployee.getString("DESIGNATION")
                        );
            }
            Table<String, String ,String > employeeAddressTable = TreeBasedTable.create();
            while (resultSetEmployeeAddress.next()){
                employeeAddressTable.put(
                        resultSetEmployeeAddress.getString("ID") ,
                        resultSetEmployeeAddress.getString("EMP_ID"),
                        resultSetEmployeeAddress.getString("ADDRESS")
                );
            }
            connection.commit();
            if(!employeeTable.isEmpty()){
                tableDataList.add(employeeTable);
            }
            if(!employeeAddressTable.isEmpty()){
                tableDataList.add(employeeAddressTable);
            }
        }
        catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //打印
        printTableRocordList(tableDataList);
    }

    /**
     * 打印表记录信息(R, C, V)
     */
    public void printTableRocordList(List<Table<String, String ,String >> tableDataList){
        for(Table<String, String ,String> table : tableDataList){
            Set<String> key = table.rowKeySet();
            Iterator<String> rIterator = key.iterator();
            while (rIterator.hasNext()){
                String R = rIterator.next();
                Map<String , String> CV = table.row(R);
                System.out.println("R=" + R +" C=" + CV.keySet().iterator().next() +" V=" + CV.values().iterator().next());
            }
            System.out.println("every table hast rows length:{}" + table.size());
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        BatchProcessing batchProcessing = new BatchProcessing();
        batchProcessing.getConnection();
        batchProcessing.createTables();
        System.out.println("==================\n");
        batchProcessing.useStatement();
        System.out.println("==================\n");
        batchProcessing.usePreparedStatement();
        System.out.println("==================\n");
        batchProcessing.findTablesDataList();
    }
}
