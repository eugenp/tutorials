package com.baeldung.jpa.projection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.baeldung.jpa.projection.model.Person;
import com.baeldung.jpa.projection.repository.AddressRepository;
import com.baeldung.jpa.projection.repository.PersonRepository;
import com.baeldung.jpa.projection.view.AddressView;
import com.baeldung.jpa.projection.view.PersonDto;
import com.baeldung.jpa.projection.view.PersonView;

@DataJpaTest
@Sql(scripts = "/projection-insert-data.sql")
@Sql(scripts = "/projection-clean-up-data.sql", executionPhase = AFTER_TEST_METHOD)
@AutoConfigurationPackage(basePackages = "com.baeldung.jpa.projection")
class JpaProjectionIntegrationTest {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void whenUsingClosedProjections_thenViewWithRequiredPropertiesIsReturned() {
        AddressView addressView = addressRepository.getAddressByState("CA").get(0);
        assertThat(addressView.getZipCode()).isEqualTo("90001");

        PersonView personView = addressView.getPerson();
        assertThat(personView.getFirstName()).isEqualTo("John");
        assertThat(personView.getLastName()).isEqualTo("Doe");
    }

    @Test
    void whenUsingOpenProjections_thenViewWithRequiredPropertiesIsReturned() {
        PersonView personView = personRepository.findByLastName("Doe");
        assertThat(personView.getFullName()).isEqualTo("John Doe");
    }

    @Test
    void whenUsingClassBasedProjections_thenDtoWithRequiredPropertiesIsReturned() {
        PersonDto personDto = personRepository.findByFirstName("John");
        assertThat(personDto.getFirstName()).isEqualTo("John");
        assertThat(personDto.getLastName()).isEqualTo("Doe");
    }

    @Test
    void whenUsingDynamicProjections_thenObjectWithRequiredPropertiesIsReturned() {
        Person person = personRepository.findByLastName("Doe", Person.class);
        PersonView personView = personRepository.findByLastName("Doe", PersonView.class);
        PersonDto personDto = personRepository.findByLastName("Doe", PersonDto.class);

        assertThat(person.getFirstName()).isEqualTo("John");
        assertThat(personView.getFirstName()).isEqualTo("John");
        assertThat(personDto.getFirstName()).isEqualTo("John");
    }
}
