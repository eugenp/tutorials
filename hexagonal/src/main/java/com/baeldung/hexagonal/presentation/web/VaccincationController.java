package com.baeldung.hexagonal.presentation.web;

import com.baeldung.hexagonal.core.ports.inbound.CreateVaccinationRequest;
import com.baeldung.hexagonal.core.ports.inbound.CreateVaccinationResponse;
import com.baeldung.hexagonal.core.ports.inbound.GetVaccinationDetailsResponse;
import com.baeldung.hexagonal.core.ports.inbound.IVaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VaccincationController {

    @Autowired
    private IVaccinationService vaccinationService;

    @RequestMapping(path = "/vaccination", method = RequestMethod.POST)
    public ResponseEntity<CreateVaccinationResponse> vaccinate(RequestEntity<CreateVaccinationRequest> requestEntity){
        CreateVaccinationRequest createVaccinationRequest = requestEntity.getBody();
        CreateVaccinationResponse createVaccinationResponse = vaccinationService.createVaccination(createVaccinationRequest);
        return new ResponseEntity<>(createVaccinationResponse, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/vaccination/{id}", method = RequestMethod.GET)
    public ResponseEntity<GetVaccinationDetailsResponse> vaccinationDetails(@PathVariable("id") String vaccinationId){
        try {
            return new ResponseEntity<>(vaccinationService.getVaccination(Integer.valueOf(vaccinationId)), HttpStatus.OK);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(new GetVaccinationDetailsResponse(), HttpStatus.BAD_REQUEST);
        }
    }
}
