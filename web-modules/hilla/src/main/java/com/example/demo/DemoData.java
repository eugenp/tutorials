package com.example.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoData implements ApplicationRunner {

    private final ContactRepository contactRepository;

    public DemoData(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        String[] firstNames = { "John", "Jane", "Alice", "Bob", "Carol", "David", "Eve", "Frank", "Grace", "Hank", "Ivy", "Jack", "Kate", "Luke", "Mary", "Ned",
            "Olga", "Paul", "Rose", "Sam" };
        String[] lastNames = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas",
            "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson" };

        for (int i = 0; i < 50; i++) {
            String name = firstNames[(int) (Math.random() * firstNames.length)] + " " + lastNames[(int) (Math.random() * lastNames.length)];
            String email = name.replaceAll(" ", ".")
                .toLowerCase() + "@example.com";
            String phone = "+1-555-" + (int) (Math.random() * 1000) + "-" + (int) (Math.random() * 10000);
            contactRepository.save(new Contact(name, email, phone));
        }

    }

}
