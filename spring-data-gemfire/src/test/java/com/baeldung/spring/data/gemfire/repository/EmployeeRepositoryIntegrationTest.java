package com.baeldung.spring.data.gemfire.repository;

import com.baeldung.spring.data.gemfire.function.FunctionExecution;
import com.baeldung.spring.data.gemfire.function.GemfireConfiguration;
import com.baeldung.spring.data.gemfire.model.Employee;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=GemfireConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class EmployeeRepositoryIntegrationTest {

    @Autowired private
    EmployeeRepository employeeRepository;

    @Autowired
    private FunctionExecution execution;

    @Test
    public void whenEmployeeIsSaved_ThenAbleToRead(){
        Employee employee=new Employee("John Davidson",4550.00);
        employeeRepository.save(employee);

        List<Employee> employees= Lists.newArrayList(employeeRepository.findAll());

        assertEquals(1, employees.size());
    }

    @Test
    public void whenSalaryGreaterThan_ThenEmployeeFound(){

        Employee employee=new Employee("John Davidson",4550.00);
        Employee employee1=new Employee("Adam Davidson",3500.00);
        Employee employee2=new Employee("Chris Davidson",5600.00);

        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> employees= Lists.newArrayList(employeeRepository.findBySalaryGreaterThan(4000.00));

        assertEquals(2,employees.size());

    }

    @Test
    public void whenSalaryLessThan_ThenEmployeeFound(){

        Employee employee=new Employee("John Davidson",4550.00);
        Employee employee1=new Employee("Adam Davidson",3500.00);
        Employee employee2=new Employee("Chris Davidson",5600.00);

        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> employees= Lists.newArrayList(employeeRepository.findBySalaryLessThan(4000));

        assertEquals(1,employees.size());

    }
    @Test
    public void whenSalaryBetween_ThenEmployeeFound(){

        Employee employee=new Employee("John Davidson",4550.00);
        Employee employee1=new Employee("Adam Davidson",3500.00);
        Employee employee2=new Employee("Chris Davidson",5600.00);

        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> employees= Lists.newArrayList(employeeRepository.findBySalaryGreaterThanAndSalaryLessThan(3500,5000));

        assertEquals(1,employees.size());

    }

    @Test
    public void whenFunctionExecutedFromClient_ThenFunctionExecutedOnServer(){
        execution.execute("Hello World");
    }

    @After
    public void cleanup(){
        employeeRepository.deleteAll();
    }
}
