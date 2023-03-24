package com.baeldung.instancio.selectors;

import com.baeldung.instancio.student.model.ContactInfo;
import com.baeldung.instancio.student.model.EmergencyContact;
import com.baeldung.instancio.student.model.Phone;
import com.baeldung.instancio.student.model.Student;
import org.instancio.Instancio;
import org.instancio.Scope;
import org.instancio.Select;
import org.instancio.TargetSelector;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.allStrings;
import static org.instancio.Select.scope;

/**
 * Examples of various selector {@link Scope}.
 * Scopes allow narrowing down selector targets.
 */
class SelectorScopesUnitTest {

    /**
     * Prefix all String fields in Phone class with "phone_".
     */
    @Test
    void whenGivenClassScope_shouldSelectTargetsWithinClass() {
        // Given
        final String prefix = "phone_";
        final Scope phoneClass = scope(Phone.class);

        // When
        Student student = Instancio.of(Student.class)
                .generate(allStrings().within(phoneClass), gen -> gen.string().prefix(prefix))
                .create();

        // Then

        // matches phone numbers
        Phone emergencyContactPhone = student.getEmergencyContact().getPhone();
        assertThat(emergencyContactPhone.getCountryCode()).startsWith(prefix);
        assertThat(emergencyContactPhone.getNumber()).startsWith(prefix);
        assertThat(student.getContactInfo().getPhones()).allSatisfy(phone -> {
            assertThat(phone.getCountryCode()).startsWith(prefix);
            assertThat(phone.getNumber()).startsWith(prefix);
        });

        // does not match other fields
        assertThat(student.getContactInfo().getAddress().getCity()).doesNotStartWith(prefix);
    }

    /**
     * Using scope to set student's and their emergency contact's
     * phone number to different values.
     */
    @Test
    void whenGivenFieldScope_shouldSelectTargetsWithinField() {
        // Given
        TargetSelector studentPhone = Select.field(Phone::getNumber).within(scope(ContactInfo.class));
        TargetSelector emergencyPhone = Select.field(Phone::getNumber).within(scope(EmergencyContact.class));

        // When
        Student student = Instancio.of(Student.class)
                .set(studentPhone, "student")
                .set(emergencyPhone, "emergency")
                .create();

        // Then
        assertThat(student.getContactInfo().getPhones())
                .isNotEmpty()
                .allSatisfy(phone -> assertThat(phone.getNumber()).isEqualTo("student"));

        assertThat(student.getEmergencyContact().getPhone().getNumber())
                .isEqualTo("emergency");
    }
}
