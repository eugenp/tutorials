package com.baeldung.inprogress.hexagonal;

import com.baeldung.inprogress.hexagonal.account.ReadOnlyAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HexagonalApplication implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(HexagonalApplication.class);

	private ReadOnlyAccountRepository readOnlyAccountRepository;

	public HexagonalApplication(ReadOnlyAccountRepository readOnlyAccountRepository) {
		this.readOnlyAccountRepository = readOnlyAccountRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(HexagonalApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		logger.info("Find account with Id 1");
		readOnlyAccountRepository.getAccountById("1");
	}
}
