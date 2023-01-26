package com.baeldung.spring.data.jpa.query.collections.vsstream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.data.jpa.collections.vsstream.User;
import com.baeldung.spring.data.jpa.collections.vsstream.UserRepository;

@DataJpaTest
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenAgeIs20_thenItShouldReturnAllUsersWhoseAgeIsGreaterThan20InAList() {
        List<User> users = userRepository.findByAgeGreaterThan(20);
        assertThat(users).isNotEmpty();
        assertThat(users.stream()
          .map(User::getAge)
          .allMatch(age -> age > 20)).isTrue();
    }

    @Test
    @Transactional
    public void whenAgeIs20_thenItShouldReturnAllUsersWhoseAgeIsGreaterThan20InAStream() {
        Stream<User> users = userRepository.findAllByAgeGreaterThan(20);
        assertThat(users).isNotNull();
        assertThat(users.map(User::getAge)
          .allMatch(age -> age > 20)).isTrue();
    }
}
