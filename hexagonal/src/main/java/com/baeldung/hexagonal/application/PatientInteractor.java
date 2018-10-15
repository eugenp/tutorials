package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.application.persistence.PatientRepository;
import com.baeldung.hexagonal.application.persistence.PersistedPatient;
import com.baeldung.hexagonal.application.persistence.TransientPatient;
import com.baeldung.hexagonal.application.registration.RegistrationRequest;
import com.baeldung.hexagonal.application.registration.RegistrationResponse;
import com.baeldung.hexagonal.domain.PatientValidationException;
import com.baeldung.hexagonal.application.registration.RegistrationPort;
import com.baeldung.hexagonal.domain.Patient;

public class PatientInteractor implements RegistrationPort {

	private PatientRepository repository;

	public PatientInteractor(PatientRepository repository) {
		this.repository = repository;
	}

	@Override
	public RegistrationResponse registerPatient(RegistrationRequest request) {
		try {
			Patient patient = Patient.from(request.getIdentifier(), request.getGivenNames(), request.getFamilyName());
			TransientPatient transientPatient = TransientPatient.from(patient.getIdentifier(), patient.getGivenNames(),
					patient.getFamilyName());
			PersistedPatient persistedPatient = repository.save(transientPatient);
			return RegistrationResponse.ok(persistedPatient.getId());
		} catch (PatientValidationException pve) {
			pve.printStackTrace();
			return RegistrationResponse.badRequest(pve.getMessage());
		}
	}
}
