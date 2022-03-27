package com.baeldung.pattern.hexagonal.eval.domain.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.baeldung.pattern.hexagonal.eval.application.adapters.AddressBookRepositoryImpl;
import com.baeldung.pattern.hexagonal.eval.application.adapters.DiallerServiceImpl;
import com.baeldung.pattern.hexagonal.eval.domain.model.Contact;

public class DiallerServiceImplUnitTest {

    private static final String NICKNAME = "Sam";
    private static final String FULL_NAME = "Samantha Smith";
    private static final String PHONE_NUMBER = "0123456789";

    @Test
    void givenContactIsRetrievable_whenDiallingContact_thenDialReturnsTrue() {
        AddressBookRepositoryImpl mockAddressBookRepositoryImpl = mock(AddressBookRepositoryImpl.class);
        when(mockAddressBookRepositoryImpl.retrieveContact(NICKNAME)).thenReturn(new Contact(FULL_NAME, PHONE_NUMBER));

        DiallerServiceImpl diallerServiceImpl = new DiallerServiceImpl(mockAddressBookRepositoryImpl);
        assert (diallerServiceImpl.dial(NICKNAME));
    }
}
