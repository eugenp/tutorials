package com.baeldung;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.spring.cloud.kubernetes.frontend.KubernetesFrontendApplication;

@SpringBootTest(classes = KubernetesFrontendApplication.class)
public class SpringContextTest {

	@Test
	public void contextLoads() {
	}

}
