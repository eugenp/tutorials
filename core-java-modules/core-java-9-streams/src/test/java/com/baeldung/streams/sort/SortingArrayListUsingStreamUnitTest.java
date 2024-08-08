package com.baeldung.streams.sort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baledung.streams.entity.Employee;

public class SortingArrayListUsingStreamUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(SortingArrayListUsingStreamUnitTest.class);

    static final Map<String, Map<String, List<Employee>>> MAP_OF_DEPT_TO_MAP_OF_SEX_TO_EMPLOYEES = new HashMap<>();

    @BeforeEach
    void setup() throws IOException {
        populateMap(getFilePath("emp_not_sorted.csv"));
    }

    private static String getFilePath(String fileName) {
        return SortingArrayListUsingStreamUnitTest.class.getClassLoader()
            .getResource(fileName)
            .getPath();

    }

    private static String[] readLinesFromFile(String filePath) throws IOException {
        return Files.readAllLines(new File(filePath).toPath())
            .toArray(new String[0]);
    }

    private static String[] getCSVDelimitedLines(List<Employee> emps) {
        return emps.stream()
            .map(emp -> emp.getDepartment() + "," + emp.getName() + "," + emp.getSalary() + "," + emp.getSex())
            .toArray(String[]::new);
    }

    private static void populateMap(String filePath) throws IOException {
        String[] lines = readLinesFromFile(filePath);
        MAP_OF_DEPT_TO_MAP_OF_SEX_TO_EMPLOYEES.clear();
        Arrays.asList(lines)
            .forEach(e -> {
                String[] strArr = e.split(",");
                Employee emp = new Employee(strArr[1], Integer.parseInt(strArr[2]), strArr[0], strArr[3]);

                MAP_OF_DEPT_TO_MAP_OF_SEX_TO_EMPLOYEES.computeIfAbsent(emp.getDepartment(),
                        k -> new HashMap<>())
                    .computeIfAbsent(emp.getSex(), k -> new ArrayList<>())
                    .add(emp);
            });
    }

    @Test
    void givenHashMapContainingEmployeeList_whenSortWithoutStreamAPI_thenSort() throws IOException {
        final List<Employee> lstOfEmployees = new ArrayList<>();

        MAP_OF_DEPT_TO_MAP_OF_SEX_TO_EMPLOYEES.forEach((dept, deptToSexToEmps) ->
            deptToSexToEmps.forEach((sex, emps) ->
            {
                emps.sort(Comparator.comparingInt(Employee::getSalary).thenComparing(Employee::getName));
                emps.forEach(this::processFurther);
                lstOfEmployees.addAll(emps);

            })
        );
        String[] expectedArray = readLinesFromFile(getFilePath("emp_sorted.csv"));
        String[] actualArray = getCSVDelimitedLines(lstOfEmployees);
        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    void givenHashMapContainingEmployeeList_whenSortWithStreamAPI_thenSort() throws IOException {
        final List<Employee> lstOfEmployees = new ArrayList<>();
        MAP_OF_DEPT_TO_MAP_OF_SEX_TO_EMPLOYEES.forEach((dept, deptToSexToEmps) ->
            deptToSexToEmps.forEach((sex, emps) ->
            {
                List<Employee> employees = emps.stream()
                    .sorted(Comparator.comparingInt(Employee::getSalary).thenComparing(Employee::getName))
                    .map(this::processFurther)
                    .collect(Collectors.toList());
                lstOfEmployees.addAll(employees);
            })
        );
        String[] expectedArray = readLinesFromFile(getFilePath("emp_sorted.csv"));
        String[] actualArray = getCSVDelimitedLines(lstOfEmployees);
        assertArrayEquals(expectedArray, actualArray);
    }

    private Employee processFurther(Employee employee) {
        //More processing
        return employee;
    }
}
