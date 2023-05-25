package com.baeldung.instancio.selectors;

import com.baeldung.instancio.student.model.Address;
import com.baeldung.instancio.student.model.ContactInfo;
import com.baeldung.instancio.student.model.Phone;
import com.baeldung.instancio.student.model.Student;
import org.instancio.FieldSelectorBuilder;
import org.instancio.Instancio;
import org.instancio.Select;
import org.instancio.Selector;
import org.instancio.SelectorGroup;
import org.instancio.TargetSelector;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.field;
import static org.instancio.Select.fields;
import static org.instancio.Select.types;

/**
 * Examples of various types of selectors provided by the {@link Select} class.
 */
class SelectorsUnitTest {

    @Test
    void whenGivenFieldSelector_shouldCustomizeSelectedField() {
        Address address = Instancio.of(Address.class)
                .set(field(Address::getCity), "London")
                .set(field(Address.class, "country"), "UK")
                .create();

        assertThat(address.getCity()).isEqualTo("London");
        assertThat(address.getCountry()).isEqualTo("UK");
    }

    @Test
    void whenGivenClassSelector_shouldCustomizeSelectedClass() {
        // Given
        final Selector allStrings = all(String.class);
        final String prefix = "test_";

        // When
        Address address = Instancio.of(Address.class)
                .generate(allStrings, gen -> gen.string().prefix(prefix))
                .create();

        // Then
        assertThat(address.getCity()).startsWith(prefix);
        assertThat(address.getCountry()).startsWith(prefix);
    }

    @Test
    void whenGivenPredicateFieldSelector_shouldCustomizeMatchingFields() {
        // Given: regie matching 'city' and 'country' fields
        final FieldSelectorBuilder fieldsMatchingRegex = fields().matching("c.*y");

        // When
        Address address = Instancio.of(Address.class)
                .ignore(fieldsMatchingRegex)
                .create();

        // Then
        assertThat(address.getCity()).isNull();
        assertThat(address.getCountry()).isNull();
        assertThat(address.getStreet()).isNotBlank();
    }

    @Test
    void whenGivenPredicateClassSelector_shouldCustomizeMatchingClasses() {
        // Given
        final TargetSelector allTypesOfCollections = types().of(Collection.class);
        final int size = 3;

        // When
        ContactInfo contactInfo = Instancio.of(ContactInfo.class)
                .generate(allTypesOfCollections, gen -> gen.collection().size(size))
                .create();

        // Then
        List<Phone> phones = contactInfo.getPhones();
        assertThat(phones).hasSize(size);
    }

    @Test
    void whenGivenSelectorGroup_shouldCustomizeSelectedFields() {
        // Given
        SelectorGroup ignoredFields = all(
                field(Student::getId),
                field(Student::getDateOfBirth));

        // When
        Student student = Instancio.of(Student.class)
                .ignore(ignoredFields)
                .create();

        // Then
        assertThat(student.getId()).isNull();
        assertThat(student.getDateOfBirth()).isNull();
    }
}
