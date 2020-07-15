package com.baeldung.hexarchi.application.cli;

import com.baeldung.hexarchi.domain.port.ingoing.AddPhoneNumberPort;
import com.baeldung.hexarchi.domain.port.outgoing.LoadPhoneBookPort;
import com.baeldung.hexarchi.domain.port.outgoing.SavePhoneBookPort;
import com.baeldung.hexarchi.domain.usecase.PhoneBookAddService;
import com.baeldung.hexarchi.infra.adapters.LoadPhoneBookAdapter;
import com.baeldung.hexarchi.infra.adapters.SavePhoneBookAdapter;
import com.baeldung.hexarchi.infra.repository.PhoneBookRepository;

public class PhoneBookApplication {
        private AddPhoneNumberPort addPhoneNumberService;
        private PhoneBookRepository phoneBookRepository;
        private SavePhoneBookPort savePhoneBookAdapter;
        private LoadPhoneBookPort loadPhoneBookAdapter;

        public void init() {
                phoneBookRepository = new PhoneBookRepository();
                savePhoneBookAdapter = new SavePhoneBookAdapter(phoneBookRepository);
                loadPhoneBookAdapter = new LoadPhoneBookAdapter(phoneBookRepository);
                addPhoneNumberService = new PhoneBookAddService(loadPhoneBookAdapter, savePhoneBookAdapter);
                addPhoneNumberService.add(100L, "064502131");
        }

        public static void main(String[] args) {
                new PhoneBookApplication().init();
        }
}
