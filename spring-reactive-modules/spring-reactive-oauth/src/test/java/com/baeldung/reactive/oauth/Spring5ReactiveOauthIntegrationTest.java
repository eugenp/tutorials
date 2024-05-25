package com.baeldung.reactive.oauth;

import com.baeldung.webclient.clientcredentials.configuration.WebClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebClientConfig.class)
public class Spring5ReactiveOauthIntegrationTest {

	@Test
	public void contextLoads() {
	}

}
