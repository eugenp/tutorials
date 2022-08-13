package com.baeldung;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.baeldung.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ratpack.test.MainClassApplicationUnderTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class ApplicationIntegrationTest {

	MainClassApplicationUnderTest appUnderTest = new MainClassApplicationUnderTest(Application.class);

	@Test
	public void givenDefaultUrl_getStaticText() {
		assertEquals("Welcome to baeldung ratpack!!!", appUnderTest.getHttpClient().getText("/"));
	}

	@Test
	public void givenDynamicUrl_getDynamicText() {
		assertEquals("Hello dummybot!!!", appUnderTest.getHttpClient().getText("/dummybot"));
	}

	@Test
	public void givenUrl_getListOfEmployee() throws JsonProcessingException {
		List<Employee> employees = new ArrayList<Employee>();
		ObjectMapper mapper = new ObjectMapper();
		employees.add(new Employee(1L, "Mr", "John Doe"));
		employees.add(new Employee(2L, "Mr", "White Snow"));

		assertEquals(mapper.writeValueAsString(employees), appUnderTest.getHttpClient().getText("/data/employees"));
	}

	@Test
	public void givenStaticUrl_getDynamicText() {
		assertEquals(21, appUnderTest.getHttpClient().getText("/randomString").length());
	}

	@After
	public void shutdown() {
		appUnderTest.close();
	}

}