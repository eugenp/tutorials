package com.baeldung.pageentityresponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class EmployeeClientUnitTest {

  @Test
  void givenRestTemplate_whenGetEmployeeDataFromExternalAPI_thenGetPageDataWithContents() {
    CustomPageImpl<EmployeeDto> mockedResponse = new CustomPageImpl<>(Arrays.asList(
        new EmployeeDto("Jane", "Finance", 50000),
        new EmployeeDto("Sarah", "IT", 70000),
        new EmployeeDto("John", "IT", 90000)
    ));

    RestTemplate restTemplate = mock(RestTemplate.class);
    ResponseEntity<CustomPageImpl<EmployeeDto>> responseEntity = new ResponseEntity<>(mockedResponse,
        HttpStatus.OK);

    String url = "http://localhost:8080/employee";
    int pageNumber = 0;
    int pageSize = 10;
    String expectedUrl = url + "?page=" + pageNumber + "&size=" + pageSize;

    HttpMethod expectedMethod = HttpMethod.GET;
    ParameterizedTypeReference<CustomPageImpl<EmployeeDto>> responseType =  new ParameterizedTypeReference<CustomPageImpl<EmployeeDto>>() {
    };

    Mockito.when(restTemplate.exchange(
        expectedUrl,
        expectedMethod,
        null,
        responseType
    )).thenReturn(responseEntity);

    EmployeeClient employeeClient = new EmployeeClient(restTemplate);

    Page<EmployeeDto> result = employeeClient.getEmployeeDataFromExternalAPI(
        PageRequest.of(pageNumber, pageSize)
    );

    verify(restTemplate).exchange(
        eq(expectedUrl),
        eq(expectedMethod),
        isNull(),
        eq(responseType)
    );
    assertEquals(1, result.getNumberOfElements());
    assertEquals(mockedResponse, result.getContent().get(0));
  }
}
