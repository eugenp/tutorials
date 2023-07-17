package com.baeldung.pageentityresponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = PageEntityResponseApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

  @Test
  void givenGetData_whenRestTemplateExchange_thenReturnsPageOfEmployee() {
      ResponseEntity<CustomPageImpl<EmployeeDto>> responseEntity = restTemplate.exchange(
        "http://localhost:" + port + "/organisation/data", HttpMethod.GET, null,
        new ParameterizedTypeReference<CustomPageImpl<EmployeeDto>>() {
        });

      assertEquals(200, responseEntity.getStatusCodeValue());
      PageImpl<EmployeeDto> restPage = responseEntity.getBody();
      assertNotNull(restPage);

      assertEquals(10, restPage.getTotalElements());

      List<EmployeeDto> content = restPage.getContent();
      assertNotNull(content);
      assertEquals(3, content.size());

      EmployeeDto employee1 = content.get(0);
      assertEquals("Jane", employee1.getName());
      assertEquals("Finance", employee1.getDept());
      assertEquals(50000, employee1.getSalary());

      EmployeeDto employee2 = content.get(1);
      assertEquals("Sarah", employee2.getName());
      assertEquals("IT", employee2.getDept());
      assertEquals(70000, employee2.getSalary());

      EmployeeDto employee3 = content.get(2);
      assertEquals("John", employee3.getName());
      assertEquals("IT", employee3.getDept());
      assertEquals(90000, employee3.getSalary());
  }

}
