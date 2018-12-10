package com.baeldung;


import com.baeldung.model.Employee;
import com.baeldung.model.Role;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

public class ActiveJDBCApp
{
    public static void main( String[] args )
    {
        try {
            Base.open();
            ActiveJDBCApp app = new ActiveJDBCApp();
            app.create();
            app.update();
            app.delete();
            app.deleteCascade();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Base.close();
        }
    }

    protected void create() {
        Employee employee = new Employee("Hugo","C","M","BN");
        employee.saveIt();
        employee.add(new Role("Java Developer","BN"));
        LazyList<Model> all = Employee.findAll();
        System.out.println(all.size());
    }

    protected void update() {
        Employee employee = Employee.findFirst("first_name = ?","Hugo");
        employee.set("last_namea","Choi").saveIt();
        employee = Employee.findFirst("last_name = ?","Choi");
        System.out.println(employee.getString("first_name") + " " + employee.getString("last_name"));
    }

    protected void delete() {
        Employee employee = Employee.findFirst("first_name = ?","Hugo");
        employee.delete();
        employee = Employee.findFirst("last_name = ?","Choi");
        if(null == employee){
            System.out.println("No such Employee found!");
        }
    }

    protected void deleteCascade() {
        create();
        Employee employee = Employee.findFirst("first_name = ?","Hugo");
        employee.deleteCascade();
        employee = Employee.findFirst("last_name = ?","C");
        if(null == employee){
            System.out.println("No such Employee found!");
        }
    }
}
