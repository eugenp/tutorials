package com.baeldung.springbootreact;

import com.baeldung.springbootreact.domain.Client;
import com.baeldung.springbootreact.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import io.github.serpro69.kfaker.Faker;

import java.util.Locale;

@Component
public class BoostrapInitialData implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final Faker faker = new Faker();

    public BoostrapInitialData(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) {
        for (int i = 0; i < 10; i++) {
            clientRepository.save(new Client(faker.getName().name(), faker.getInternet().safeEmail()));
        }
    }
}
