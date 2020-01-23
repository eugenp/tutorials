package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.unit.DataSize;

@SpringBootTest
@TestPropertySource("classpath:spring-conversion-test.properties")
public class SpringPropertiesConversionUnitTest {

	@Autowired
	private PropertyConversion propertyConversion;

	@Test
	void whenUsingSpringDefaultSizeConversion_thenDataSizeObjectIsSet() {
		
		assertEquals(DataSize.ofMegabytes(500), propertyConversion.getUploadSpeed());
		assertEquals(DataSize.ofGigabytes(10), propertyConversion.getDownloadSpeed());
	}

	@Test
	void whenUsingSpringDefaultDurationConversion_thenDurationObjectIsSet() {
		
		assertEquals(Duration.ofDays(1), propertyConversion.getBackupDay());
		assertEquals(Duration.ofHours(8), propertyConversion.getBackupHour());
	}
	
	@Test
	void whenRegisteringCustomCredentialsConverter_thenCredentialsAreParsed() {
		
		assertEquals("user", propertyConversion.getCredentials().getUsername());
		assertEquals("123", propertyConversion.getCredentials().getPassword());
	}
}