package kerberos.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;

@SpringBootApplication
class KerberosClientApp {

	static {
		System.setProperty("java.security.krb5.conf",
				Paths.get(".\\spring-security-sso\\spring-security-sso-kerberos\\krb-test-workdir/krb5.conf").normalize().toAbsolutePath().toString());
		System.setProperty("sun.security.krb5.debug", "true");
		System.setProperty("http.use.global.creds", "false");
	}

	public static void main(String[] args) {
		SpringApplication.run(KerberosClientApp.class, args);
	}
}
