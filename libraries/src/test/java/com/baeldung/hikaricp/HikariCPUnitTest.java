package com.baeldung.hikaricp;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HikariCPUnitTest {

    @Test
    public void givenConnection_thenFetchDbData() {
        List<Employee> employees = HikariCPDemo.fetchData();
        assertEquals(4, employees.size());
    }

}
