package com.baeldung.hikaricp;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HikariCPTest {

	@Test
	@Ignore
    public void givenConnection_thenFetchDbData() {
		List<Employee> employees = HikariCPDemo.fetchData();
		assertEquals(4, employees.size());
	}
	
}
