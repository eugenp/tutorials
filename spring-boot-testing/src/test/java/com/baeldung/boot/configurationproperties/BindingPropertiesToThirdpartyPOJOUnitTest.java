package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:thirdparty-config-test.properties")
public class BindingPropertiesToThirdpartyPOJOUnitTest {

	@Autowired
	private ConnectionFactory connectionfactory;

	@Test
	void givenThirdPartyPOJO_whenBindingPropertiesFile_thenAllFieldsAreSet() {
		
		assertEquals("foo", connectionfactory.getTestingConnection().getAuthorization());
		assertEquals(50, connectionfactory.getTestingConnection().getTimeout());
		
		assertEquals("bar", connectionfactory.getLiveConnection().getAuthorization());
		assertEquals(100, connectionfactory.getLiveConnection().getTimeout());
	}
}