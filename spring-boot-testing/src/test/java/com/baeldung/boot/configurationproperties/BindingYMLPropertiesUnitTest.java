package com.baeldung.boot.configurationproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

		assertEquals(Set.of("img1.png", "img2.png"),
				serverConfig.getImgIds());

		assertEquals("192.168.0.2", serverConfig.getAddress().getIp());
		assertEquals(5000, serverConfig.getAddress().getPort());

		assertEquals(Map.of("imgs", "/etc/imgs", "html", "/etc/html"), 
				serverConfig.getDirs());
	}
}