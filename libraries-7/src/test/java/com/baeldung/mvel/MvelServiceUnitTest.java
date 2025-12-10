package com.baeldung.mvel;

import org.junit.jupiter.api.Test;
import org.mvel2.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import com.baeldung.mvel.model.Employee;

public class MvelServiceUnitTest {

    @Test
    public void givenSimpleExpression_whenEvaluate_thenCorrectResult() {
        String expression = "5 + 3 * 2";
        Object result = MVEL.eval(expression);

        assertEquals(11, result);
    }

    @Test
    public void givenVariables_whenEvaluateExpression_thenCorrectResult() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("salary", 75000.0);
        variables.put("experience", 8);

        String expression = "salary * 0.10";
        Object result = MVEL.eval(expression, variables);

        assertEquals(7500.0, result);
    }

    @Test
    public void givenBooleanExpression_whenEvaluate_thenCorrectResult() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("experience", 12);
        variables.put("salary", 95000.0);

        String expression = "experience > 10 && salary > 80000";
        Object result = MVEL.eval(expression, variables);

        assertTrue((Boolean) result);
    }

    @Test
    public void givenNullSafeNavigation_whenPropertyIsNull_thenNoException() {
        Employee.Job job = new Employee.Job(5, 5000);
        Employee employee = new Employee("David", job);
        employee.getJob()
            .setDepartment(null);
        String expression = " (job.?department.length) ";
        Object result = MVEL.eval(expression, employee);

        assertNull(result);
    }

    @Test
    public void givenEmployee_whenAccessProperties_thenCorrectValues() {
        Employee.Job job = new Employee.Job(8, 75000.0);
        Employee employee = new Employee("John Doe", job);

        String expression = "name + ' has ' + job.experienceInYears + ' years of experience'";
        Object result = MVEL.eval(expression, employee);

        assertEquals("John Doe has 8 years of experience", result);
    }

    @Test
    public void givenEmployee_whenCallMethod_thenCorrectResult() {
        Employee.Job job = new Employee.Job(12, 90000.0);
        Employee employee = new Employee("Jane Smith", job);

        String expression = "job.calculateAnnualBonus()";
        Object result = MVEL.eval(expression, employee);

        assertEquals(18000.0, (Double) result, 0.01);
    }

    @Test
    public void givenEmployee_whenEvaluateConditional_thenCorrectResult() {
        Employee.Job job = new Employee.Job(15, 120000.0);
        Employee employee = new Employee("Bob Johnson", job);

        String expression = "if (job.experienceInYears > 10) { job.salary * 0.20 } " + "else if (job.experienceInYears > 5) { job.salary * 0.10 } " +
            "else { job.salary * 0.05 }";
        Object result = MVEL.eval(expression, employee);

        assertEquals(24000.0, (Double) result, 0.01);
    }

    @Test
    public void givenCompiledExpression_whenExecuteMultipleTimes_thenCorrectResults() {
        String expression = "job.experienceInYears > 5 && job.salary > 50000";
        Serializable compiledExpression = MVEL.compileExpression(expression);

        Employee.Job job1 = new Employee.Job(7, 60000.0);
        Employee employee1 = new Employee("Alice", job1);

        Employee.Job job2 = new Employee.Job(3, 45000.0);
        Employee employee2 = new Employee("Charlie", job2);

        boolean result1 = (Boolean) MVEL.executeExpression(compiledExpression, employee1);
        boolean result2 = (Boolean) MVEL.executeExpression(compiledExpression, employee2);

        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    public void givenListProjection_whenFilter_thenCorrectResult() {
        Map<String, Object> variables = new HashMap<>();
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        variables.put("numbers", numbers);

        String expression = "($ in numbers if $ > 5)";
        Object result = MVEL.eval(expression, variables);

        assertInstanceOf(List.class, result);
        List<?> filteredList = (List<?>) result;
        assertEquals(5, filteredList.size());
    }

    @Test
    public void givenListTransformation_whenProject_thenCorrectResult() {
        Map<String, Object> variables = new HashMap<>();
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        variables.put("numbers", numbers);

        String expression = "($ * 2 in numbers)";
        Object result = MVEL.eval(expression, variables);

        assertInstanceOf(List.class, result);
        List<?> transformedList = (List<?>) result;
        assertEquals(10, transformedList.get(4));
    }

}

