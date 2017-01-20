package com.ek.test.steps;

/**
 * Created by s738204 on 23/12/2016.
 */
import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class EmployeeJSONTest {
    public static void main(String args[]){
        EmployeeJSONTest empJSONTest = new EmployeeJSONTest();

        try {

            Employee emp = new Employee();
            emp.setId(10);
            emp.setName("John");
            empJSONTest.writeJSON(emp);

            Employee empJson = empJSONTest.readJSON();
            System.out.println(empJson);

        }
        catch (JsonParseException e) { e.printStackTrace(); }
        catch (JsonMappingException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    @Test
    public void employeeJSONTest() {
        EmployeeJSONTest empJSONTest = new EmployeeJSONTest();
        try {

            Employee emp = new Employee();
            emp.setId(10);
            emp.setName("John");
            empJSONTest.writeJSON(emp);

            Employee empJson = empJSONTest.readJSON();
            System.out.println(empJson);
        } catch (JsonParseException e) { e.printStackTrace(); }
          catch (JsonMappingException e) { e.printStackTrace(); }
          catch (IOException e) { e.printStackTrace(); }
    }


    private void writeJSON(Employee emp) throws JsonGenerationException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("employee.json"), emp);      // Specify the path where you want to store your Employee JSON file
    }

    private Employee readJSON() throws JsonParseException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        Employee emp = mapper.readValue(new File("employee.json"), Employee.class);
        return emp;
    }
}



class Employee {
    private String name;
    private int id;

    public Employee(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return "Employee [ Employee Name: "+name+", Employee Id: "+ id+ " ]";
    }
}
