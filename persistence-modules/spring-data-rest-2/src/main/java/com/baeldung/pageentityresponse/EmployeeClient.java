package com.baeldung.pageentityresponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class EmployeeClient {
    private final RestTemplate restTemplate;

    public EmployeeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Page<EmployeeDto> getEmployeeDataFromExternalAPI(Pageable pageable) {
        String url = "http://localhost:8080/external-service/employee";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url)
          .queryParam("page", pageable.getPageNumber())
          .queryParam("size", pageable.getPageSize());

        ResponseEntity<CustomPageImpl<EmployeeDto>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<EmployeeDto>>() {
        });

        return responseEntity.getBody();
    }
}
