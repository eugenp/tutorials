package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:server-config-test.properties")
public class BindingPropertiesToUserDefinedPOJOUnitTest {

	@Autowired
	private ServerConfig serverConfig;

	@Test
	void givenUserDefinedPOJO_whenBindingPropertiesFile_thenAllFieldsAreSet() {

		assertEquals("node1", serverConfig.getName());

		assertEquals(Set.of("img1.jpg", "img2.jpg"),
				serverConfig.getImgIds());

		assertEquals("192.168.0.1", serverConfig.getAddress().getIp());
		assertEquals(8099, serverConfig.getAddress().getPort());

		assertEquals(Map.of("imgs", "/root/imgs", "html", "/root/html"), 
				serverConfig.getDirs());
	}
}