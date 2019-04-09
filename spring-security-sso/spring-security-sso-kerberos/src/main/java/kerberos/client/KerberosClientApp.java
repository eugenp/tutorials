package kerberos.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;

@SpringBootApplication
class KerberosClientApp {

	static {
		System.setProperty("java.security.krb5.conf",
				Paths.get(".\\krb-test-workdir\\krb5.conf").normalize().toAbsolutePath().toString());
		System.setProperty("sun.security.krb5.debug", "true");
	}

	public static void main(String[] args) {
		SpringApplication.run(KerberosClientApp.class, args);
	}
}
