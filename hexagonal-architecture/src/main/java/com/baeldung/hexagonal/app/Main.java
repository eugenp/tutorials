package com.baeldung.hexagonal.app;

import com.baeldung.hexagonal.app.model.Contact;
import com.baeldung.hexagonal.app.repository.ContactRepository;
import com.baeldung.hexagonal.app.repository.impl.ContactRepositoryImpl;
import com.baeldung.hexagonal.app.service.ContactService;
import com.baeldung.hexagonal.app.service.impl.ContactServiceImpl;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Contact contact = new Contact();
        contact.setName(readLine(in, "Name"));
        contact.setPhone(readLine(in, "Phone"));
        ContactRepository contactRepository = new ContactRepositoryImpl("/home/user/contacts.txt");
        ContactService contactService = new ContactServiceImpl(contactRepository);
        contactService.addContact(contact);
    }

    private static String readLine(Scanner in, String input) {
        System.out.print(String.format("%s%s", input, " : "));
        return in.nextLine();
    }
}

