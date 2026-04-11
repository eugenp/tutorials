package com.baeldung.spring.aotrepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.spring.aotrepository.entity.User;
import com.baeldung.spring.aotrepository.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(classes = Application.class)
@Transactional
class ExtendingRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test
    void givenUserRepository_whenFindById_thenCorrect() {
        User user = new User("firstname", "lastname");

        User saved = userRepository.save(user);

        assertThat(saved).isNotNull();

        List<User> allById = userRepository.findAllById(List.of(saved.getId()));

        assertThat(allById).hasSize(1);
        assertThat("firstname").isEqualTo(allById.getFirst().getFirstName());
    }
}
