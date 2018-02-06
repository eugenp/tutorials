package com.baeldung.sqlinjection.springdata;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.sqlinjection.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeRepoTest {

    @Autowired
    private EmployeeRepository empRepository;

    private void initializeData() {
        Employee emp1 = new Employee(1L, "Trudy");
        Employee emp2 = new Employee(2L, "Bob");
        empRepository.save(emp1);
        empRepository.save(emp2);
    }

    @Test
    public void givenEmployees_whenSearchWithGoodQuery_thenFindOk() {
        initializeData();

        // search by name
        List<Employee> result = empRepository.findByName("Trudy");

        assertTrue(result.size() == 1);
        assertTrue(result.get(0)
            .getName()
            .equalsIgnoreCase("Trudy"));
    }

    @Test
    public void givenEmployees_whenSearchWithBadQuery_thenNoHarm() {
        initializeData();

        // search by name with injected malice to return all records instead
        List<Employee> result = empRepository.findByNameQuery("Trudy' or 1=1 or '1'='1", null);

        assertTrue(result.size() == 0);
    }

    @Test
    public void givenEmployees_whenSearchWithOrderByBadQuery_thenPotentialHarm() {

        initializeData();

        // search by name with injected malice to return all records instead
        try {
            empRepository.findByNameQuery("Trudy' or 1=1 or '1'='1", new Sort(" length(emp.name))"));
        } catch (InvalidDataAccessApiUsageException exp) {
            // Since it accepts function it is potentially dangerous!
            // Spring Data throws an exception in this case
            assertTrue(true);
            return;
        }

        assertTrue("Potential harm not prevented", 1 == 2);
    }
}
