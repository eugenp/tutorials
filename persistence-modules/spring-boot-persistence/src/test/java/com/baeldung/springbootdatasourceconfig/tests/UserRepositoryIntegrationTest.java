package com.baeldung.springbootdatasourceconfig.tests;

import com.baeldung.springbootdatasourceconfig.application.entities.User;
import com.baeldung.springbootdatasourceconfig.application.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void whenCalledSave_thenCorrectNumberOfUsers() {
        userRepository.save(new User("Bob", "bob@domain.com"));
        List<User> users = (List<User>) userRepository.findAll();
        
        assertThat(users.size()).isEqualTo(1);
    }
}
