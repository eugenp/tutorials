package com.baeldung.hexagonal.core.components.application;

import com.baeldung.hexagonal.core.components.domain.VaccinationDetails;
import com.baeldung.hexagonal.core.ports.inbound.CreateVaccinationRequest;
import com.baeldung.hexagonal.core.ports.inbound.CreateVaccinationResponse;
import com.baeldung.hexagonal.core.ports.inbound.GetVaccinationDetailsResponse;
import com.baeldung.hexagonal.core.ports.inbound.IVaccinationService;
import com.baeldung.hexagonal.core.ports.outbound.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class VaccinationService implements IVaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Override
    public CreateVaccinationResponse createVaccination(CreateVaccinationRequest request){
        VaccinationDetails vaccinationDetailsPersisted = vaccinationRepository.save(getVaccinationDetails(request));
        CreateVaccinationResponse createVaccinationResponse = new CreateVaccinationResponse();
        createVaccinationResponse.setPersonId(vaccinationDetailsPersisted.getPersonId());
        createVaccinationResponse.setFirstDoseDone(vaccinationDetailsPersisted.getFirstDoseDone());
        createVaccinationResponse.setSecondDoseDone(vaccinationDetailsPersisted.getSecondDoseDone());
        return createVaccinationResponse;
    }

    @Override
    public GetVaccinationDetailsResponse getVaccination(Integer vaccinationId){
        Optional<VaccinationDetails> vaccinationDetailsOptional = vaccinationRepository.findById(vaccinationId);
        if(vaccinationDetailsOptional.isPresent()){
            return getGetVaccinationDetailsResponse(vaccinationDetailsOptional);
        }
        throw new RuntimeException("Data Not found");
    }

    private GetVaccinationDetailsResponse getGetVaccinationDetailsResponse(Optional<VaccinationDetails> vaccinationDetailsOptional) {
        VaccinationDetails vaccinationDetails = vaccinationDetailsOptional.get();
        GetVaccinationDetailsResponse response = new GetVaccinationDetailsResponse();
        response.setCenterDetails(vaccinationDetails.getCenterDetails());
        response.setFirstDose(vaccinationDetails.getFirstDose());
        response.setPersonId(vaccinationDetails.getPersonId());
        response.setSecondDose(vaccinationDetails.getSecondDose());
        response.setVaccinationId(vaccinationDetails.getVaccinationId());
        response.setFirstDoseDone(vaccinationDetails.getFirstDoseDone());
        response.setSecondDoseDone(vaccinationDetails.getSecondDoseDone());
        return response;
    }

    private VaccinationDetails getVaccinationDetails(CreateVaccinationRequest request) {
        VaccinationDetails vaccinationDetails = new VaccinationDetails();
        vaccinationDetails.setVaccinationId(Integer.valueOf(request.getPersonId()));
        vaccinationDetails.setCreated(new Date());
        vaccinationDetails.setLastModified(new Date());
        vaccinationDetails.setPersonId(request.getPersonId());
        vaccinationDetails.setFirstDose(request.getFirstDoseDate());
        vaccinationDetails.setSecondDose(request.getSecondDoseDate());
        vaccinationDetails.setCenterDetails("Hospital XYZ");
        if(request.getFirstDoseDate() != null) {
            vaccinationDetails.setFirstDoseDone(Boolean.TRUE);
        }
        if(request.getSecondDoseDate() != null) {
            vaccinationDetails.setSecondDoseDone(Boolean.TRUE);
        }
        return vaccinationDetails;
    }
}
