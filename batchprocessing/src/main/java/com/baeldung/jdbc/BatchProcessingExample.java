package com.baeldung.jdbc;

import com.baeldung.jdbc.model.Employee;
import com.baeldung.jdbc.model.EmployeeAddress;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class BatchProcessingExample {

    private final String[] EMPLOYEES = new String[]{"Zuck","Mike","Larry","Musk","Steve"};
    private final String[] DESIGNATIONS = new String[]{"CFO","CSO","CTO","CEO","CMO"};
    private final String[] ADDRESSES = new String[]{"China","York","Diego","Carolina","India"};

    Connection connection;

    @PostConstruct
    public void init(){
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:EmployeeDatabase","SA","");
            connection.setAutoCommit(false);
            connection.createStatement().executeUpdate("create table EMPLOYEE (ID VARCHAR(36), NAME VARCHAR(45), DESIGNATION VARCHAR(15))");
            connection.createStatement().executeUpdate("create table EMP_ADDRESS (ID VARCHAR(36), EMP_ID VARCHAR(36), ADDRESS VARCHAR(45))");
            System.out.println("Tables Created!!!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping("/batchprocessing")
    public String index(){
        return "Batch Processing JDBC - baeldung";
    }

    @RequestMapping(value = "/batchprocessing/employees",method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getListOfEmployees(){
        try {
            String selectSQL = "SELECT EMP.*,ADDR.ID ADDR_ID,ADDR.ADDRESS FROM EMPLOYEE EMP LEFT OUTER JOIN EMP_ADDRESS ADDR ON EMP.ID = ADDR.EMP_ID";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);
            List<Employee> employees = new ArrayList<>(0);
            while (resultSet.next()){
                String empId = resultSet.getString("ID");
                String name = resultSet.getString("NAME");
                String designation = resultSet.getString("DESIGNATION");
                String addr_id = resultSet.getString("ADDR_ID");
                String address = resultSet.getString("ADDRESS");
                Employee emp = new Employee(empId, name, designation);
                EmployeeAddress addr = new EmployeeAddress(addr_id,address);
                emp.setAddress(addr);
                employees.add(emp);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(0), INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/batchprocessing/statement/employees",method = RequestMethod.POST)
    public ResponseEntity<String> addMultipleEmployeesUsingStatement(){
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
            return new ResponseEntity<>("Employees Added Successfully using Statement", CREATED);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error during rollback");
                System.out.println(ex.getMessage());
            }
            e.printStackTrace(System.out);
            return new ResponseEntity<>("Error during Employee Addition using Statement", INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/batchprocessing/preparedstatement/employees",method = RequestMethod.POST)
    public ResponseEntity<String> addMultipleEmployeesUsingPreparedStatement(){
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
            return new ResponseEntity<>("Employees Added Successfully using PreparedStatement", CREATED);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error during rollback");
                System.out.println(ex.getMessage());
            }
            e.printStackTrace(System.out);
            return new ResponseEntity<>("Error during Employee Addition using PreparedStatement", INTERNAL_SERVER_ERROR);
        }
    }
}
