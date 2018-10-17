package org.baeldung.web.service;

import java.net.URI;

import org.baeldung.SpringTestConfig;
import org.baeldung.web.dto.EmployeeDto;
import org.baeldung.web.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.baeldung.web.service.EmployeeService.EMP_URL_PREFIX;
import static org.baeldung.web.service.EmployeeService.URL_SEP;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class EmployeeServiceMockRestServiceServerUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceMockRestServiceServerUnitTest.class);

    @Autowired
    private EmployeeService empService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void initMocks() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void givenMockingIsDoneByMockRestServiceServer_whenGetIsCalled_shouldReturnMockedObject() throws Exception {
        String id = "E001";
        Employee emp = new Employee();
        emp.setId(id);
        emp.setName("Eric Simmons");
        emp.setSalary(10000.00d);

        String fullUri = new StringBuilder().append(EMP_URL_PREFIX).append(URL_SEP)
          .append(id).toString();

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(fullUri)))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapper.writeValueAsString(emp)));

        EmployeeDto employeeDto = empService.getEmployee(id);
        logger.info("Employee received as: {}", employeeDto);

        mockServer.verify();
        Assert.assertEquals(emp.getName(), employeeDto.getName());
        Assert.assertEquals(emp.getId(), employeeDto.getId());
    }

}
