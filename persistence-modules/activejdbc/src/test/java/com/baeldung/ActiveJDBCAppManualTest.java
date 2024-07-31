package com.baeldung;

import com.baeldung.model.Employee;
import com.baeldung.model.Role;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.Test;

import java.util.List;

public class ActiveJDBCAppManualTest extends DBSpec
{
    @Test
    public void ifEmployeeCreated_thenIsValid() {
        Employee employee = new Employee("B", "N", "M", "BN");
        the(employee).shouldBe("valid");
    }

    @Test
    public void ifEmployeeCreatedWithRoles_thenShouldPersist() {
        Employee employee = new Employee("B", "N", "M", "BN");
        employee.saveIt();
        employee.add(new Role("Java Developer","BN"));
        employee.add(new Role("Lead Java Developer","BN"));
        a(Role.count()).shouldBeEqual(2);
        List<Role> roles = employee.getAll(Role.class).orderBy("created_at");
        the(roles.get(0).getRoleName()).shouldBeEqual("Java Developer");
        the(roles.get(1).getRoleName()).shouldBeEqual("Lead Java Developer");
    }

    @Test
    public void ifEmployeeCreatedWithRoles_whenNameUpdated_thenShouldShowNewName() {
        Employee employee = new Employee("Binesh", "N", "M", "BN");
        employee.saveIt();
        employee.add(new Role("Java Developer","BN"));
        employee.add(new Role("Lead Java Developer","BN"));
        employee = Employee.findFirst("first_name = ?", "Binesh");
        employee.set("last_name","Narayanan").saveIt();
        Employee updated = Employee.findFirst("first_name = ?", "Binesh");
        the(updated.getLastName()).shouldBeEqual("Narayanan");
    }

    @Test
    public void ifEmployeeCreatedWithRoles_whenDeleted_thenShouldNotBeFound() {
        Employee employee = new Employee("Binesh", "N", "M", "BN");
        employee.saveIt();
        employee.add(new Role("Java Developer","BN"));
        employee.delete();
        employee = Employee.findFirst("first_name = ?", "Binesh");
        the(employee).shouldBeNull();
    }
}
