package com.baeldung.hexarchi.infra.adapters;

import com.baeldung.hexarchi.domain.entities.PhoneBook;
import com.baeldung.hexarchi.domain.port.outgoing.SavePhoneBookPort;
import com.baeldung.hexarchi.infra.repository.PhoneBookRepository;

public class SavePhoneBookAdapter implements SavePhoneBookPort {

    private PhoneBookRepository phoneBookRepository;

    public SavePhoneBookAdapter(PhoneBookRepository phoneBookRepository) {
        this.phoneBookRepository = phoneBookRepository;
    }

    @Override
    public void save(PhoneBook phoneBook) {
        phoneBookRepository.save(phoneBook);
    }
}
