package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/*
 To run this test we need to run the databases first.
 A dedicated docker-compose.yml file is located under the resources directory.
 We can run it by simple executing `docker-compose up`.
 */
@SpringJUnitConfig
@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class YugabyteDBLiveTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void givenTwoUsers_whenPersistUsingJPARepository_thenUserAreSaved() {
	    User user1 = new User();
	    user1.setName("Alex");
	    User user2 = new User();
	    user2.setName("John");
	    userRepository.save(user1);
	    userRepository.save(user2);
	    List<User> allUsers = userRepository.findAll();
	    assertEquals(2, allUsers.size());
	}
}
