package com.baeldung.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BatchprocessingApplicationTests {

	private MockMvc mvc;

	@InjectMocks
	private BatchProcessingExample target = new BatchProcessingExample();

	@Mock
	private Connection connection;

	@Mock
	private ResultSet resultSet;

	@Mock
	private Statement statement;

	@Mock
	private PreparedStatement employeeStatement;

	@Mock
	private PreparedStatement employeeAddressStatement;

	@Before
	public void before(){
		MockitoAnnotations.initMocks(this);
		this.mvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	@Test
	public void when_indexInvoked_then_return_200_OK_withWelcomeMessage() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/batchprocessing")
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void when_getEmployees_then_return_200_OK_withEmployeeListJson() throws Exception {
		Mockito.when(connection.createStatement()).thenReturn(statement);
		String selectSQL = "SELECT EMP.*,ADDR.ID ADDR_ID,ADDR.ADDRESS FROM EMPLOYEE EMP LEFT OUTER JOIN EMP_ADDRESS ADDR ON EMP.ID = ADDR.EMP_ID";
		Mockito.when(statement.executeQuery(selectSQL)).thenReturn(resultSet);
		Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(resultSet.getString("ID")).thenReturn(UUID.randomUUID().toString()).thenReturn(UUID.randomUUID().toString()).thenReturn(UUID.randomUUID().toString());
		Mockito.when(resultSet.getString("NAME")).thenReturn("NAME1").thenReturn("NAME2").thenReturn("NAME3");
		Mockito.when(resultSet.getString("DESIGNATION")).thenReturn("DESIGNATION1").thenReturn("DESIGNATION2").thenReturn("DESIGNATION3");
		Mockito.when(resultSet.getString("ADDR_ID")).thenReturn(UUID.randomUUID().toString()).thenReturn(UUID.randomUUID().toString()).thenReturn(UUID.randomUUID().toString());
		Mockito.when(resultSet.getString("ADDRESS")).thenReturn("ADDRS_1").thenReturn("ADDRS_1").thenReturn("ADDRS_1");
		mvc.perform(MockMvcRequestBuilders.get("/batchprocessing/employees")
				.accept(APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void when_addMultipleEmployeesUsingStatement_then_return_201_CREATED() throws Exception {
		Mockito.when(connection.createStatement()).thenReturn(statement);
		mvc.perform(MockMvcRequestBuilders.post("/batchprocessing/statement/employees")
				.accept(APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void when_addMultipleEmployeesUsingPreparedStatement_then_return_201_CREATED() throws Exception {
		String insertEmployeeSQL = "INSERT INTO EMPLOYEE(ID, NAME, DESIGNATION) VALUES (?,?,?);";
		String insertEmployeeAddrSQL = "INSERT INTO EMP_ADDRESS(ID, EMP_ID, ADDRESS) VALUES (?,?,?);";
		Mockito.when(connection.prepareStatement(insertEmployeeSQL)).thenReturn(employeeStatement);
		Mockito.when(connection.prepareStatement(insertEmployeeAddrSQL)).thenReturn(employeeAddressStatement);
		mvc.perform(MockMvcRequestBuilders.post("/batchprocessing/preparedstatement/employees")
				.accept(APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
}
