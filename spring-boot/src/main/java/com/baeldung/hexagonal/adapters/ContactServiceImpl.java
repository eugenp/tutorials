package com.baeldung.hexagonal.adapters;

import java.util.Collection;

import com.baeldung.hexagonal.models.Contact;
import com.baeldung.hexagonal.ports.ContactRepository;
import com.baeldung.hexagonal.ports.ContactService;
import com.baeldung.hexagonal.ports.EmailValidator;
import com.baeldung.hexagonal.ports.PhoneValidator;

public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;
    private EmailValidator emailValidator;
    private PhoneValidator phoneValidator;

    public ContactServiceImpl(ContactRepository contactRepository, EmailValidator emailValidator, PhoneValidator phoneValidator) {
        this.contactRepository = contactRepository;
        this.emailValidator = emailValidator;
        this.phoneValidator = phoneValidator;
    }

    @Override
    public Contact create(Contact contact) {
       if (emailValidator.validate(contact.getEmail()) && phoneValidator.validate(contact.getPhone())) {
            return contactRepository.create(contact);
        }
        return null;
    }

    @Override
    public Contact read(String id) {
        return contactRepository.read(id);
    }

    @Override
    public Contact update(Contact contact) {
       if (emailValidator.validate(contact.getEmail()) && phoneValidator.validate(contact.getPhone())) {
            return contactRepository.create(contact);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        contactRepository.delete(id);
    }

    @Override
    public Collection<Contact> list() {
        return contactRepository.list();
    }

}
