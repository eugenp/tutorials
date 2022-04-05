package com.baeldung.mock;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.baeldung.SpringTestConfig;
import com.baeldung.resttemplate.web.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringTestConfig.class)
public class EmployeeServiceMockRestServiceServerUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceMockRestServiceServerUnitTest.class);

    @Autowired
    private EmployeeService empService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void givenMockingIsDoneByMockRestServiceServer_whenGetIsCalled_shouldReturnMockedObject() throws Exception {
        Employee emp = new Employee("E001", "Eric Simmons");

        mockServer.expect(ExpectedCount.once(),
          requestTo(new URI("http://localhost:8080/employee/E001")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
              .contentType(MediaType.APPLICATION_JSON)
              .body(mapper.writeValueAsString(emp)));

        Employee employee = empService.getEmployee("E001");
        mockServer.verify();
        Assertions.assertEquals(emp, employee);
    }

}
