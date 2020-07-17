package com.baeldung.hexarchi.domain.port.outgoing;

import com.baeldung.hexarchi.domain.entities.PhoneBook;

public interface SavePhoneBookPort {
    void save(PhoneBook phoneBook);
}
