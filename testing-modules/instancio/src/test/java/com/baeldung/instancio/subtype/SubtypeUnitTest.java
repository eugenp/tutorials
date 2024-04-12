package com.baeldung.instancio.subtype;

import com.baeldung.instancio.abstracttype.AbstractItem;
import com.baeldung.instancio.generics.Item;
import com.baeldung.instancio.student.model.ContactInfo;
import com.baeldung.instancio.student.model.Student;
import org.instancio.Instancio;
import org.instancio.TypeToken;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.field;

/**
 * Using {@code subtype()} method we can specify a specific implementation
 * class for an abstract type, or a specialized subclass for a concrete class.
 */
class SubtypeUnitTest {

    @Test
    void whenGivenCollectionSubtype_shouldUseSpecifiedCollectionClass() {
        // Given
        final Class<?> subtype = LinkedList.class;

        // When
        Student student = Instancio.of(Student.class)
                .subtype(field(ContactInfo::getPhones), subtype)
                .create();

        // Then
        assertThat(student.getContactInfo().getPhones())
                .isNotEmpty()
                .isExactlyInstanceOf(subtype);
    }

    @Test
    void whenGivenSubtypeForGenericAbstractType_shouldUseSpecifiedConcreteClass() {
        // Given
        final Class<?> subtype = Item.class;

        // When
        AbstractItem<LocalDateTime> abstractItem = Instancio.of(new TypeToken<AbstractItem<LocalDateTime>>() {})
                .subtype(all(AbstractItem.class), subtype)
                .create();

        // Then
        assertThat(abstractItem).isExactlyInstanceOf(subtype);
        assertThat(abstractItem.getValue()).isNotNull();
    }
}
