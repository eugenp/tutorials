package com.baeldung.mvel;

import com.baeldung.mvel.model.Employee;

import org.junit.jupiter.api.Test;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MvelTemplateServiceUnitTest {

    @Test
    public void givenEmployeeData_whenGenerateReport_thenCorrectOutput() {
        String template = """
            Employee Report
            ================
            Name: @{name}
            Experience: @{experience} years
            Salary: $@{salary
            """;

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "John Doe");
        variables.put("experience", 12);
        variables.put("salary", 95000.0);

        CompiledTemplate compiledTemplate = TemplateCompiler.compileTemplate(template);
        String result = (String) TemplateRuntime.execute(compiledTemplate, variables);

        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("12 years"));
    }

    @Test
    public void givenSeniorEmployee_whenGenerateReport_thenCorrectStatus() {
        String template = """
            Employee Report
            @if{experience > 10}
            Status: Senior Employee\n
            @else{}
            Status: Junior/Mid-level Employee
            @end{}
            """;

        Map<String, Object> variables = new HashMap<>();
        variables.put("experience", 12);

        CompiledTemplate compiledTemplate = TemplateCompiler.compileTemplate(template);
        String result = (String) TemplateRuntime.execute(compiledTemplate, variables);

        assertTrue(result.contains("Senior Employee"));
    }

    @Test
    public void givenEmployeeList_whenGenerateListReport_thenCorrectOutput() {
        String template = """
            "Employee List:
            "@foreach{emp : employees}
            "- @{emp.name} (@{emp.job.department})
            "@end{}
            """;

        List<Employee> employees = Arrays.asList(new Employee("John", 10, 80000.0, "IT"), new Employee("Jane", 8, 75000.0, "HR"),
            new Employee("Bob", 12, 95000.0, "Finance"));

        Map<String, Object> variables = new HashMap<>();
        variables.put("employees", employees);

        CompiledTemplate compiledTemplate = TemplateCompiler.compileTemplate(template);
        String result = (String) TemplateRuntime.execute(compiledTemplate, variables);

        assertTrue(result.contains("John (IT)"));
        assertTrue(result.contains("Jane (HR)"));
    }
}

