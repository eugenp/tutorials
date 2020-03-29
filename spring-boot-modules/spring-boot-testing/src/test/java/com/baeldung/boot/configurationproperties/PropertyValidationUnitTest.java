package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = MailServer.class)
@TestPropertySource("classpath:property-validation-test.properties")
public class PropertyValidationUnitTest {

    @Autowired
    private MailServer mailServer;

    private static Validator propertyValidator;

    @BeforeAll
    public static void setup() {
        propertyValidator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    void whenBindingPropertiesToValidatedBeans_thenConstrainsAreChecked() {
        assertEquals(0, propertyValidator.validate(mailServer.getPropertiesMap())
            .size());
        assertEquals(0, propertyValidator.validate(mailServer.getMailConfig())
            .size());
    }
}