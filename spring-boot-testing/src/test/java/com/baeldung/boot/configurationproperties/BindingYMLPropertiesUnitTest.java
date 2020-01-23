package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BindingYMLPropertiesUnitTest {
	
	@Autowired
	private ServerConfig serverConfig;
	
	@Test
	void whenBindingYMLConfigFile_thenAllFieldsAreSet() {
		
		assertEquals("node2", serverConfig.getName());

		Set<String> expectedImgs = new HashSet<>();
		expectedImgs.add("img1.png");
		expectedImgs.add("img2.png");
		assertEquals(expectedImgs, serverConfig.getImgIds());

		assertEquals("192.168.0.2", serverConfig.getAddress().getIp());
		assertEquals(5000, serverConfig.getAddress().getPort());

		Map<String, String> expectedDirs = new HashMap<>();
		expectedDirs.put("imgs", "/etc/imgs");
		expectedDirs.put("html", "/etc/html");
		assertEquals(expectedDirs, serverConfig.getDirs());
	}
}