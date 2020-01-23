package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
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

		Set<String> expectedImgs = new HashSet<>();
		expectedImgs.add("img1.jpg");
		expectedImgs.add("img2.jpg");
		assertEquals(expectedImgs, serverConfig.getImgIds());

		assertEquals("192.168.0.1", serverConfig.getAddress().getIp());
		assertEquals(8099, serverConfig.getAddress().getPort());

		Map<String, String> expectedDirs = new HashMap<>();
		expectedDirs.put("imgs", "/root/imgs");
		expectedDirs.put("html", "/root/html");
		assertEquals(expectedDirs, serverConfig.getDirs());
	}
}