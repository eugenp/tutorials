package com.baeldung.client;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.baeldung.client.rest.RestClient;
import com.baeldung.server.model.Employee;

public class JerseyClientLiveTest {

    public static final int HTTP_CREATED = 201;

    private RestClient client = new RestClient();

    @Test
    public void givenCorrectObject_whenCorrectJsonRequest_thenResponseCodeCreated() {
        Employee emp = new Employee(6, "Johny");

        Response response = client.createJsonEmployee(emp);

        assert(response.getStatus() == HTTP_CREATED);
    }

    @Test
    public void givenCorrectObject_whenCorrectXmlRequest_thenResponseCodeCreated() {
        Employee emp = new Employee(7, "Jacky");

        Response response = client.createXmlEmployee(emp);

        assert(response.getStatus() == HTTP_CREATED);
    }

    @Test
    public void givenCorrectId_whenCorrectJsonRequest_thenCorrectEmployeeRetrieved() {
        int employeeId = 1;

        Employee emp = client.getJsonEmployee(employeeId);

        assert(emp.getFirstName().equals("Jane"));
    }

    @Test
    public void givenCorrectId_whenCorrectXmlRequest_thenCorrectEmployeeRetrieved() {
        int employeeId = 1;

        Employee emp = client.getXmlEmployee(employeeId);

        assert(emp.getFirstName().equals("Jane"));
    }
}
