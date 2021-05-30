package com.shaheen.hexa.core.usecase.getperson;

import com.shaheen.hexa.core.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetPersonService implements GetPersonUseCase {
    private final GetPersonPort getPersonPort;

    @Autowired
    public GetPersonService(final GetPersonPort getPersonPort) {
        this.getPersonPort = getPersonPort;
    }

    @Override
    public Person getPerson(final Long id) {
        return getPersonPort.getPerson(id);
    }
}
