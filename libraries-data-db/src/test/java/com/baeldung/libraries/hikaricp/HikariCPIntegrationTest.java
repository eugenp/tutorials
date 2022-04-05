package com.baeldung.libraries.hikaricp;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HikariCPIntegrationTest {

    @Test
    public void givenConnection_thenFetchDbData() {
        List<Employee> employees = HikariCPDemo.fetchData();
        assertEquals(4, employees.size());
    }

}
