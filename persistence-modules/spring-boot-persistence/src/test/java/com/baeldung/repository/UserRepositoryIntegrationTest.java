package com.baeldung.repository;

import com.baeldung.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adam.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired private UserRepository userRepository;

    @Test
    public void givenTwoImportFilesWhenFindAllShouldReturnSixUsers() {
        Collection<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(9);
    }

}
