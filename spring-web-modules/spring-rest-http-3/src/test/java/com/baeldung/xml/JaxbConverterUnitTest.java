package com.baeldung.xml;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.baeldung.xml.controller.User;
import com.baeldung.xml.util.JaxbConverter;
import org.junit.jupiter.api.Test;

class JaxbConverterUnitTest {
    private static final User USER = new User(1L, "John", "Doe");
    private static final JaxbConverter<User> converter = new JaxbConverter<>(User.class);
    @Test
    void givenJConverterWhenProvideXmlThenJaxbConvertsCorrectlyBackAndForth() {
        String xmlString = converter.convertToXml(USER);
        User actual = converter.convertFromXml(xmlString);
        assertThat(actual).isEqualTo(USER);
    }

}
