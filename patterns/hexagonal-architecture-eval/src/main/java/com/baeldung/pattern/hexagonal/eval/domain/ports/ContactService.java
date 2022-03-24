package main.java.com.baeldung.pattern.hexagonal.eval.domain.ports;

import main.java.com.baeldung.pattern.hexagonal.eval.domain.model.Contact;

public interface ContactService {

    Contact create(String title, String firstName, String lastName, String internationalCode, String number);
}
