package com.baeldung.pattern.javahexagonalarch.dao;

import com.baeldung.pattern.javahexagonalarch.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository public class EmployeeDaoImpl implements EmployeeDao {

        private static Map<Integer, Employee> employeeList = new HashMap<Integer, Employee>();

        public ArrayList<Employee> getAllEmployees() {
                return new ArrayList<Employee>(employeeList.values());
        }

        public void addEmployee(Employee e) {
                employeeList.put(e.getEmployeeId(), e);
        }

        public Employee getEmployeesById(int id) {
                return employeeList.get(id);
        }

}
