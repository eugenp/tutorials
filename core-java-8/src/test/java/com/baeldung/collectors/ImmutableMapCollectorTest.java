package com.baeldung.collectors;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.Employee;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class ImmutableMapCollectorTest {

    private ImmutableMapCollector<Employee, Integer, Builder<Integer, Employee>, ImmutableMap<Integer, Employee>> immutableMapCollector;
    List<Employee> empList = new ArrayList<Employee>();
    DoubleSummaryStatistics stats = new DoubleSummaryStatistics();

    @Before
    public void setUp() {

        empList.add(new Employee(1, "John", 100000, 1));
        empList.add(new Employee(2, "Joe", 200000, 2));
        empList.add(new Employee(3, "Smith", 300000, 3));
        empList.add(new Employee(4, "Jack", 900000, 1));
        empList.add(new Employee(5, "Alex", 500000, 3));
        empList.add(new Employee(6, "Justin", 800000, 2));
        empList.add(new Employee(7, "Bob", 700000, 1));

        stats.accept(empList.get(0).getSalary());
        stats.accept(empList.get(1).getSalary());
        stats.accept(empList.get(2).getSalary());
        stats.accept(empList.get(3).getSalary());
        stats.accept(empList.get(4).getSalary());
        stats.accept(empList.get(5).getSalary());
        stats.accept(empList.get(6).getSalary());

        immutableMapCollector = new ImmutableMapCollector<Employee, Integer, ImmutableMap.Builder<Integer, Employee>, ImmutableMap<Integer, Employee>>();

        immutableMapCollector.setUp((Employee e) -> e.getEmpId());

        empList.stream().collect(immutableMapCollector);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void joiningTest() {
        Assert.assertEquals("", immutableMapCollector.joining(), "<1,2,3>");
    }

    @Test
    public void mappingDeptEmployeeSalaryTest() {
        Map<Integer, Set<Long>> deptSalaryMap = new HashMap<Integer, Set<Long>>();
        Set<Long> dept1SalarySet = new HashSet<Long>();
        dept1SalarySet.add(100000L);
        dept1SalarySet.add(900000L);
        dept1SalarySet.add(700000L);
        deptSalaryMap.put(1, dept1SalarySet);
        Set<Long> dept2SalarySet = new HashSet<Long>();
        dept2SalarySet.add(200000L);
        dept2SalarySet.add(800000L);
        deptSalaryMap.put(2, dept2SalarySet);
        Set<Long> dept3SalarySet = new HashSet<Long>();
        dept3SalarySet.add(300000L);
        dept3SalarySet.add(500000L);
        deptSalaryMap.put(3, dept3SalarySet);
        Assert.assertEquals("Mapping employee salaries to department", immutableMapCollector.mappingDeptEmpSal(empList), deptSalaryMap);
    }

    @Test
    public void averagingEmployeeSalary() {
        Assert.assertEquals("", immutableMapCollector.averagingDouble(empList), 500000.0, 0);
    }

    @Test
    public void groupingByDeptIdReducingByMaxSalary() {
        Map<Integer, Optional<Employee>> result = new HashMap<Integer, Optional<Employee>>();
        result.put(1, Optional.of(empList.get(3)));
        result.put(2, Optional.of(empList.get(5)));
        result.put(3, Optional.of(empList.get(4)));
        Assert.assertEquals("Grouping by department id reducing by maximum salary", immutableMapCollector.groupByReducingMax(empList), result);
    }

    @Test
    public void maxSalaryEmployee() {
        Assert.assertEquals("Employee with maximum salary", immutableMapCollector.employeeWithMaxSalary(empList).get().getSalary(), empList.get(3).getSalary());
    }

    @Test
    public void minSalaryEmployee() {
        Assert.assertEquals("Employee with minimum salary", immutableMapCollector.employeeWithMinSalary(empList).get().getSalary(), empList.get(0).getSalary());
    }

    @Test
    public void summarizingDouble() {
        Assert.assertEquals("Statistics of employee salaries", immutableMapCollector.summarizingEmployeeSalaryDouble(empList).toString(), stats.toString());
    }

    @Test
    public void summingDouble() {
        Assert.assertEquals("Sum of all employee salaries", immutableMapCollector.summingEmployeeSalaryDouble(empList), new Double(stats.getSum()));
    }
}
