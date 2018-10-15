package com.baeldung.hexagonal.framework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.baeldung.hexagonal.application.persistence.PatientRepository;
import com.baeldung.hexagonal.application.persistence.PersistedPatient;
import com.baeldung.hexagonal.application.persistence.TransientPatient;

public class InMemoryPatientRepository implements PatientRepository {

	private static final AtomicLong SEQUENCE = new AtomicLong();
	private static final Map<Long, PersistedPatient> STORE = new ConcurrentHashMap<>();

	@Override
	public synchronized PersistedPatient save(TransientPatient transientPatient) {
		Long id = SEQUENCE.incrementAndGet();
		PersistedPatient persistedPatient = PersistedPatient.from(id, transientPatient);
		STORE.put(id, persistedPatient);
		return persistedPatient;
	}
}
