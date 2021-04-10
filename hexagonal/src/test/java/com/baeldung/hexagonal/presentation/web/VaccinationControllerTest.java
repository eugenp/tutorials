package com.baeldung.hexagonal.presentation.web;

import com.baeldung.hexagonal.core.ports.inbound.CreateVaccinationRequest;
import com.baeldung.hexagonal.core.ports.inbound.CreateVaccinationResponse;
import com.baeldung.hexagonal.core.ports.inbound.GetVaccinationDetailsResponse;
import com.baeldung.hexagonal.core.ports.inbound.IVaccinationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Date;

public class VaccinationControllerTest {

    @InjectMocks
    private VaccincationController vaccincationController;

    @Mock
    private IVaccinationService iVaccinationService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVaccinate(){

        CreateVaccinationRequest createVaccinationRequest = new CreateVaccinationRequest();
        RequestEntity<CreateVaccinationRequest> requestEntity = new RequestEntity<>(createVaccinationRequest, HttpMethod.POST, URI.create("http://localhost:099"));

        CreateVaccinationResponse createVaccinationResponse = new CreateVaccinationResponse();
        createVaccinationResponse.setPersonId("123");
        Mockito.when(iVaccinationService.createVaccination(Mockito.any())).thenReturn(createVaccinationResponse);

        ResponseEntity<CreateVaccinationResponse> responseEntity = vaccincationController.vaccinate(requestEntity);

        Assertions.assertEquals("123",responseEntity.getBody().getPersonId());

    }

    @Test
    public void testVaccinationDetails(){
        GetVaccinationDetailsResponse getVaccinationDetailsResponse = new GetVaccinationDetailsResponse();
        getVaccinationDetailsResponse.setVaccinationId(1111);
        getVaccinationDetailsResponse.setPersonId("123");
        Mockito.when(iVaccinationService.getVaccination(Mockito.any())).thenReturn(getVaccinationDetailsResponse);

        ResponseEntity<GetVaccinationDetailsResponse> responseEntity = vaccincationController.vaccinationDetails("1111");

        Assertions.assertEquals(1111, responseEntity.getBody().getVaccinationId());
    }
}
