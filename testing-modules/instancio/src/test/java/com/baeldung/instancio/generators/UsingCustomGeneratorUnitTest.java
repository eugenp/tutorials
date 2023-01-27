package com.baeldung.instancio.generators;

import com.baeldung.instancio.student.model.Address;
import com.baeldung.instancio.student.model.ContactInfo;
import com.baeldung.instancio.student.model.Phone;
import com.baeldung.instancio.student.model.Student;
import org.instancio.Instancio;
import org.instancio.Random;
import org.instancio.generator.AfterGenerate;
import org.instancio.generator.Generator;
import org.instancio.generator.Hints;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.field;

class UsingCustomGeneratorUnitTest {
    private static final String ARGENTINA = "Argentina";
    private static final int PHONES_SIZE = 5;

    private static final Generator<Address> ADDRESS_GENERATOR = new Generator<Address>() {
        @Override
        public Address generate(final Random random) {
            Address address = new Address();
            address.setCountry(ARGENTINA);
            return address;
        }

        @Override
        public Hints hints() {
            // The hint telling the engine to populate any field that has a null value
            return Hints.afterGenerate(AfterGenerate.POPULATE_NULLS);
        }
    };

    @Test
    void whenGivenAGenerator_objectShouldBeCreatedUsingCustomGenerator() {
        Student student = Instancio.of(Student.class)
                .supply(all(Address.class), ADDRESS_GENERATOR)
                .generate(field(ContactInfo::getPhones), gen -> gen.collection().size(PHONES_SIZE))
                .create();

        ContactInfo contactInfo = student.getContactInfo();
        Address address = contactInfo.getAddress();
        List<Phone> phones = contactInfo.getPhones();

        assertThat(phones).hasSize(PHONES_SIZE);
        assertThat(address.getCountry()).isEqualTo(ARGENTINA);
        // null fields were populated with random values
        assertThat(address.getStreet()).isNotNull();
        assertThat(address.getCity()).isNotNull();
    }

}
