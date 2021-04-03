package com.baeldung.hexagonal.core.ports.inbound;

public interface IVaccinationService {
    CreateVaccinationResponse createVaccination(CreateVaccinationRequest request);
    GetVaccinationDetailsResponse getVaccination(Integer id);
}
