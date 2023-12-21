package com.baeldung.springjunitconfiguration;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class SpringJUnitConfigurationUnitTest {

    @ParameterizedTest
    @ValueSource(strings = { "Dilbert", "Wally" })
    void givenPeopleList_whenSetPeopleWithName_thenListContainsOnePerson(String name, @Autowired List<Person> people) {
        assertThat(people.stream()
          .map(Person::getName)
          .filter(name::equals)).hasSize(1);
    }
}
