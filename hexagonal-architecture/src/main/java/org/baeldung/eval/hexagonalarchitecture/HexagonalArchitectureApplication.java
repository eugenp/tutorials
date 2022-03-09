package org.baeldung.eval.hexagonalarchitecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HexagonalArchitectureApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexagonalArchitectureApplication.class, args);
	}
}
