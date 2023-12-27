package com.baeldung.spring.data.persistence.findvsget;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.spring.data.persistence.findvsget.entity.User;
import com.baeldung.spring.data.persistence.findvsget.service.SimpleUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("findBy test:")
class FindUserIntegrationIntegrationTest extends DatabaseConfigurationBaseIntegrationTest {


    @Autowired
    private SimpleUserService service;

    @ParameterizedTest
    @ArgumentsSource(UserProvider.class)
    @DisplayName("when looking for a user by an existing ID returns a user")
    void whenGettingUserByCorrectIdThenReturnUser(Long id, String firstName, String lastName) {
        final User expected = new User(id, firstName, lastName);
        final User actual = service.findUser(id);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("when looking for a user by a non-existing ID returns null")
    @ValueSource(longs = {11, 12, 13})
    void whenGettingUserByIncorrectIdThenReturnNull(Long id) {
        assertThat(service.findUser(id)).isNull();
    }


}
