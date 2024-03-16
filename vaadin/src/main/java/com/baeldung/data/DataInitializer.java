package com.baeldung.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ContactRepository contactRepository;

    public DataInitializer(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    private final List<String> firstNames = List.of(
            "John", "Jane", "Emily", "Michael", "Sarah", "James", "Mary", "Robert",
            "Patricia", "William", "Linda", "David", "Barbara", "Richard", "Susan",
            "Joseph", "Jessica", "Thomas", "Karen", "Charles"
    );
    private final List<String> lastNames = List.of(
            "Doe", "Smith", "Johnson", "Brown", "Davis", "Miller", "Wilson", "Moore",
            "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin",
            "Thompson", "Garcia", "Martinez", "Robinson", "Clark"
    );

    @Override
    public void run(String... args) throws Exception {
        var contacts = new ArrayList<Contact>();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            String email = String.format("%s.%s@example.com", firstName.toLowerCase(), lastName.toLowerCase());
            String phone = String.format("555-%03d-%04d", random.nextInt(1000), random.nextInt(10000));

            contacts.add(new Contact(firstName + " " + lastName, email, phone));
        }

        contactRepository.saveAll(contacts);
    }
}
