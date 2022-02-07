package com.baeldung.hexagonal.architecture.adapters.driving;

import com.baeldung.hexagonal.architecture.core.domain.Person;
import com.baeldung.hexagonal.architecture.core.ports.inbound.PersonService;
import com.baeldung.hexagonal.architecture.core.ports.outbound.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;

    @Override
    public Person findById(Long id) {
        return personRepository.findById(id);
    }
}
