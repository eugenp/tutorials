package com.baeldung.hexarchi.infra.adapters;

import com.baeldung.hexarchi.domain.entities.PhoneBook;
import com.baeldung.hexarchi.domain.port.outgoing.LoadPhoneBookPort;
import com.baeldung.hexarchi.infra.repository.PhoneBookRepository;

import java.util.Optional;

public class LoadPhoneBookAdapter implements LoadPhoneBookPort {

        private PhoneBookRepository phoneBookRepository;

        public LoadPhoneBookAdapter(PhoneBookRepository phoneBookRepository) {
                this.phoneBookRepository = phoneBookRepository;
        }

        @Override public Optional<PhoneBook> load(Long id) {
                return phoneBookRepository.load(id);
        }
}
