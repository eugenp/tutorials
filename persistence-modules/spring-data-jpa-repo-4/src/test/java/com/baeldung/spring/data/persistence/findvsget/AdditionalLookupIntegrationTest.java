package com.baeldung.spring.data.persistence.findvsget;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertInsertCount;
import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.baeldung.spring.data.persistence.findvsget.entity.Group;
import com.baeldung.spring.data.persistence.findvsget.entity.User;
import com.baeldung.spring.data.persistence.findvsget.repository.GroupRepository;
import com.baeldung.spring.data.persistence.findvsget.repository.SimpleUserRepository;
import io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator;
import java.util.Optional;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class AdditionalLookupIntegrationTest extends DatabaseConfigurationBaseIntegrationTest {

    @Autowired
    private SimpleUserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    void setup() {
        SQLStatementCountValidator.reset();
    }

    @Test
    void givenEmptyGroup_whenAssigningAdministratorWithGetByReference_thenNoAdditionalLookupHappens() {
        User user = userRepository.getReferenceById(1L);
        Group group = new Group();
        group.setAdministrator(user);
        groupRepository.save(group);
        assertSelectCount(0);
        assertInsertCount(1);
    }

    @Test
    void givenEmptyGroup_whenAssigningIncorrectAdministratorWithGetByReference_thenErrorIsThrown() {
        User user = userRepository.getReferenceById(-1L);
        Group group = new Group();
        group.setAdministrator(user);
        assertThatExceptionOfType(DataIntegrityViolationException.class)
          .isThrownBy(() -> {
              groupRepository.save(group);
          });
        assertSelectCount(0);
    }

    @Test
    void givenEmptyGroup_whenAssigningAdministratorWithFindBy_thenAdditionalLookupHappens() {
        Optional<User> optionalUser = userRepository.findById(1L);
        assertThat(optionalUser).isPresent();
        User user = optionalUser.get();
        Group group = new Group();
        group.setAdministrator(user);
        groupRepository.save(group);
        assertSelectCount(2);
        assertInsertCount(1);
    }

    @Test
    void givenEmptyGroup_whenAddingUserWithGetByReference_thenTryToAccessInternalsAndThrowError() {
        User user = userRepository.getReferenceById(1L);
        Group group = new Group();
        assertThatExceptionOfType(LazyInitializationException.class)
          .isThrownBy(() -> {
              group.addUser(user);
          });
    }

    @Test
    void givenEmptyGroup_whenAddingUserWithFindBy_thenAdditionalLookupHappens() {
        Optional<User> optionalUser = userRepository.findById(1L);
        assertThat(optionalUser).isPresent();
        User user = optionalUser.get();
        Group group = new Group();
        group.addUser(user);
        groupRepository.save(group);
        assertSelectCount(1);
        assertInsertCount(1);
    }
}
