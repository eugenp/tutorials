package com.baeldung.springbootdatasourceconfig.tests;

import com.baeldung.springbootdatasourceconfig.entities.User;
import com.baeldung.springbootdatasourceconfig.repositories.UserRepository;
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
    public void whenCalledfindById_thenCorrect() {
        userRepository.save(new User("Julie", "julie@domain.com"));
        
        assertThat(userRepository.findById(1L)).isInstanceOf(Optional.class);
    }
    
    @Test
    public void whenCalledfindAll_thenCorrect() {
        userRepository.save(new User("John", "john@domain.com"));
        userRepository.save(new User("Jennifer", "jennifer@domain.com"));
        
        assertThat(userRepository.findAll()).isInstanceOf(List.class);
    }
    
    @Test
    public void whenCalledSave_thenCorrect() {
        userRepository.save(new User("Bob", "bob@domain.com"));
        User customer = userRepository.findById(4L).orElseGet(() -> new User("John", "john@domain.com"));
        
        assertThat(customer.getName()).isEqualTo("Bob");
    }
}
