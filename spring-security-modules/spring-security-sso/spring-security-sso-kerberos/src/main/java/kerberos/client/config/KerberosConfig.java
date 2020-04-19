package kerberos.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.kerberos.client.KerberosRestTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
class KerberosConfig {

	@Value("${app.user-principal}")
	private String principal;

	@Value("${app.keytab-location}")
	private String keytabLocation;

	@Bean
	public RestTemplate restTemplate() {
		return new KerberosRestTemplate(keytabLocation, principal);
	}
}
