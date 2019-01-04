package com.baeldung.hexagonal.app.repository.impl;

import com.baeldung.hexagonal.app.model.Contact;
import com.baeldung.hexagonal.app.repository.ContactRepository;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ContactRepositoryImpl implements ContactRepository {

    private File file;

    public ContactRepositoryImpl(String file) {
        this.file = new File(file);
    }

    @Override
    public void save(Contact contact) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(contact.getName() + "," + contact.getPhone() + "\n");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
