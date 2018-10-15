package com.baeldung.hexagonal.application.persistence;

public interface PatientRepository {

	PersistedPatient save(TransientPatient transientPatient);
}
