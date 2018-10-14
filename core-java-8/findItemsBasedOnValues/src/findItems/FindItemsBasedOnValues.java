package findItems;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class FindItemsBasedOnValues {

    List<Employee> EmplList = new ArrayList<Employee>();
    List<Department> deptList = new ArrayList<Department>();

    public static void main(String[] args) throws ParseException {
        FindItemsBasedOnValues findItems = new FindItemsBasedOnValues();
        findItems.givenDepartmentList_thenEmployeeListIsFilteredCorrectly();
    }

    @Test
    public void givenDepartmentList_thenEmployeeListIsFilteredCorrectly() {
        Integer expectedId = 1002;

        populate(EmplList, deptList);

        List<Employee> filteredList = EmplList.stream()
            .filter(empl -> deptList.stream()
                .anyMatch(dept -> dept.getDepartment()
                    .equals("sales") && empl.getEmployeeId()
                    .equals(dept.getEmployeeId())))
            .collect(Collectors.toList());

        assertEquals(expectedId, filteredList.get(0)
            .getEmployeeId());
    }

    private void populate(List<Employee> EmplList, List<Department> deptList) {
        Employee employee1 = new Employee(1001, "empl1");
        Employee employee2 = new Employee(1002, "empl2");
        Employee employee3 = new Employee(1003, "empl3");

        Collections.addAll(EmplList, employee1, employee2, employee3);

        Department department1 = new Department(1002, "sales");
        Department department2 = new Department(1003, "marketing");
        Department department3 = new Department(1004, "sales");

        Collections.addAll(deptList, department1, department2, department3);
    }
}

class Employee {
    Integer employeeId;
    String employeeName;

    public Employee(Integer employeeId, String employeeName) {
        super();
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

}

class Department {
    Integer employeeId;
    String department;

    public Department(Integer employeeId, String department) {
        super();
        this.employeeId = employeeId;
        this.department = department;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getDepartment() {
        return department;
    }

}