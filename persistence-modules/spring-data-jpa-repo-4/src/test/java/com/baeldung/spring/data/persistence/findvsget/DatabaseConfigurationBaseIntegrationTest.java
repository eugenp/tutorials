package com.baeldung.spring.data.persistence.findvsget;

import static com.baeldung.spring.data.persistence.findvsget.UserProvider.userSource;
import static org.assertj.core.api.Assumptions.assumeThat;

import com.baeldung.spring.data.persistence.findvsget.entity.User;
import com.baeldung.spring.data.persistence.findvsget.repository.GroupRepository;
import com.baeldung.spring.data.persistence.findvsget.repository.SimpleUserRepository;
import com.baeldung.spring.data.persistence.util.TestConfig;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ApplicationConfig.class, TestConfig.class}, properties = {
    "spring.jpa.generate-ddl=true",
    "spring.jpa.show-sql=false"
})
abstract class DatabaseConfigurationBaseIntegrationTest {

    private static final int NUMBER_OF_USERS = 10;

    @Autowired
    private SimpleUserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    void populateDatabase() {
        final List<User> users = userSource()
            .map(Arguments::get)
            .map(s -> new User(((Long) s[0]), s[1].toString(), s[2].toString()))
            .collect(Collectors.toList());
        userRepository.saveAll(users);
        assumeThat(userRepository.findAll()).hasSize(NUMBER_OF_USERS);
    }

    @AfterEach
    void clearDatabase() {
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

}

