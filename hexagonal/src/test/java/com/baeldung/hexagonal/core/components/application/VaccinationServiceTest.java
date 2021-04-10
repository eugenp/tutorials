package com.baeldung.hexagonal.core.components.application;

import com.baeldung.hexagonal.core.components.domain.VaccinationDetails;
import com.baeldung.hexagonal.core.ports.inbound.CreateVaccinationRequest;
import com.baeldung.hexagonal.core.ports.inbound.CreateVaccinationResponse;
import com.baeldung.hexagonal.core.ports.inbound.GetVaccinationDetailsResponse;
import com.baeldung.hexagonal.core.ports.outbound.VaccinationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class VaccinationServiceTest {

    @InjectMocks
    private VaccinationService vaccinationService;

    @Mock
    private VaccinationRepository vaccinationRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVaccination(){
        VaccinationDetails vaccinationDetails = new VaccinationDetails();
        vaccinationDetails.setVaccinationId(123);
        vaccinationDetails.setPersonId("123456");
        vaccinationDetails.setCenterDetails("local");
        Mockito.when(vaccinationRepository.save(Mockito.any())).thenReturn(vaccinationDetails);
        CreateVaccinationRequest createVaccinationRequest = new CreateVaccinationRequest();
        createVaccinationRequest.setPersonId("123456");
        CreateVaccinationResponse createVaccinationResponse = vaccinationService.createVaccination(createVaccinationRequest);

        Assertions.assertEquals("123456", createVaccinationResponse.getPersonId());

    }

    @Test
    public void testGetVaccination(){
        VaccinationDetails vaccinationDetails = new VaccinationDetails();
        vaccinationDetails.setVaccinationId(123);
        vaccinationDetails.setPersonId("123456");
        vaccinationDetails.setCenterDetails("local");
        Mockito.when(vaccinationRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(vaccinationDetails));

        GetVaccinationDetailsResponse response = vaccinationService.getVaccination(123456);

        Assertions.assertEquals("123456", response.getPersonId());
    }
}
