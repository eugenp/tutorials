package com.baeldung.hexagonal;

import java.util.Collections;

import com.baeldung.hexagonal.application.registration.RegistrationResponse;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.baeldung.hexagonal.application.PatientInteractor;
import com.baeldung.hexagonal.application.persistence.PatientRepository;
import com.baeldung.hexagonal.application.registration.RegistrationPort;
import com.baeldung.hexagonal.application.registration.RegistrationRequest;
import com.baeldung.hexagonal.framework.InMemoryPatientRepository;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class IntegrationTest {

	private PatientRepository repository = new InMemoryPatientRepository();
	private RegistrationPort registrationPort = new PatientInteractor(repository);

	@Test
	public void givenEmptyRepository_whenPatientIsRegistered_thenPatientIsStored() {
		RegistrationRequest request = RegistrationRequest.from(
				"1234567890", Collections.singletonList("joe"), "bloggs");

		RegistrationResponse expectedResponse = RegistrationResponse.ok(1L);

		RegistrationResponse response = registrationPort.registerPatient(request);

		assertThat(response, is(equalTo(expectedResponse)));
	}
}
