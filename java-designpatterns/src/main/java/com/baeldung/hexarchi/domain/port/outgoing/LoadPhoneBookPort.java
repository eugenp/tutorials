package com.baeldung.hexarchi.domain.port.outgoing;

import com.baeldung.hexarchi.domain.entities.PhoneBook;

import java.util.Optional;

public interface LoadPhoneBookPort {
    Optional<PhoneBook> load(Long id);
}
