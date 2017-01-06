package com.baeldung.server;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.baeldung.server.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JerseyApiLiveTest {

    @Test
    public void getAllEmployees_ifCorrectRequest_ResponseCodeSuccess() throws ClientProtocolException, IOException {
        final HttpUriRequest request = new HttpGet("http://localhost:8082/JerseyTutorial/resources/employees");
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assert(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
    }

    @Test
    public void getEmployee_ifEmployeeExists_ResponseCodeSuccess() throws ClientProtocolException, IOException {
        final HttpUriRequest request = new HttpGet("http://localhost:8082/JerseyTutorial/resources/employees/1");
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assert(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
    }

    @Test
    public void getEmployee_ifEmployeeDoesNotExist_ResponseCodeNotFound() throws ClientProtocolException, IOException {
        final HttpUriRequest request = new HttpGet("http://localhost:8082/JerseyTutorial/resources/employees/1000");
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assert(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getEmployee_ifJsonRequested_CorrectDataRetrieved() throws ClientProtocolException, IOException {
        final HttpUriRequest request = new HttpGet("http://localhost:8082/JerseyTutorial/resources/employees/1");
        request.setHeader("Accept", "application/json");
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        ObjectMapper mapper = new ObjectMapper();
        Employee emp = mapper.readValue(httpResponse.getEntity().getContent(), Employee.class);
        assert(emp.getFirstName().equals("Jane"));
    }

    @Test
    public void addEmployee_ifJsonRequestSent_ResponseCodeCreated() throws ClientProtocolException, IOException {
        final HttpPost request = new HttpPost("http://localhost:8082/JerseyTutorial/resources/employees");
        Employee emp = new Employee(5, "Johny", "Doe", 33);
        ObjectMapper mapper = new ObjectMapper();
        String empJson = mapper.writeValueAsString(emp);
        StringEntity input = new StringEntity(empJson);
        input.setContentType("application/json");
        request.setEntity(input);
        final HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        assert(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED);
    }

}