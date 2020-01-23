package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:property-validation-test.properties")
public class PropertyValidationUnitTest {

	@Autowired
	private MailServer mailServer;

	private static Validator propertyValidator;

	@BeforeAll
	public static void setup() {
		propertyValidator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	void whenBindingPropertiesToValidatedBeans_thenConstrainsAreChecked() {
		
		assertEquals(0, propertyValidator.validate(mailServer.getPropertiesMap()).size());
		assertEquals(0, propertyValidator.validate(mailServer.getMailConfig()).size());
	}
}