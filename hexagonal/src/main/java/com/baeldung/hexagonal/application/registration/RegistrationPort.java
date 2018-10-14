package com.baeldung.hexagonal.application.registration;

public interface RegistrationPort {

	RegistrationResponse registerPatient(RegistrationRequest request);
}
