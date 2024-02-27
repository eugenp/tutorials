package com.baeldung.spring.data.persistence.findvsget;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.baeldung.spring.data.persistence.findvsget.entity.Group;
import com.baeldung.spring.data.persistence.findvsget.entity.User;
import com.baeldung.spring.data.persistence.findvsget.repository.GroupRepository;
import com.baeldung.spring.data.persistence.findvsget.repository.SimpleUserRepository;
import java.util.Optional;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class AdditionalLookupIntegrationTest extends DatabaseConfigurationBaseIntegrationTest {

    @Autowired
    private SimpleUserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    void givenEmptyGroup_whenAssigningAdministratorWithGetByReference_thenNoAdditionalLookupHappens() {
        User user = userRepository.getReferenceById(1L);
        Group group = new Group();
        group.setAdministrator(user);
        groupRepository.save(group);
    }

    @Test
    void givenEmptyGroup_whenAssigningIncorrectAdministratorWithGetByReference_thenNoAdditionalLookupHappens() {
        User user = userRepository.getReferenceById(-1L);
        Group group = new Group();
        group.setAdministrator(user);
        assertThatExceptionOfType(DataIntegrityViolationException.class)
          .isThrownBy(() -> {
              groupRepository.save(group);
          });
    }

    @Test
    void givenEmptyGroup_whenAssigningAdministratorWithFindBy_thenNoAdditionalLookupHappens() {
        Optional<User> optionalUser = userRepository.findById(1L);
        assertThat(optionalUser).isPresent();
        User user = optionalUser.get();
        Group group = new Group();
        group.setAdministrator(user);
        groupRepository.save(group);
    }

    @Test
    void givenEmptyGroup_whenAddingUserWithGetByReference_thenNoAdditionalLookupHappens() {
        User user = userRepository.getReferenceById(1L);
        Group group = new Group();
        assertThatExceptionOfType(LazyInitializationException.class)
          .isThrownBy(() -> {
              group.addUser(user);
          });
    }

    @Test
    void givenEmptyGroup_whenAddingUserWithFindBy_thenNoAdditionalLookupHappens() {
        Optional<User> optionalUser = userRepository.findById(1L);
        assertThat(optionalUser).isPresent();
        User user = optionalUser.get();
        Group group = new Group();
        group.addUser(user);
        groupRepository.save(group);
    }
}
