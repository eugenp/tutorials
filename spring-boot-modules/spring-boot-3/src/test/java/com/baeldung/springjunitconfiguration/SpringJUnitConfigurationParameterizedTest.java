package com.baeldung.springjunitconfiguration;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@SpringJUnitConfig(classes = TestConfig.class , loader = AnnotationConfigContextLoader.class)
public class SpringJUnitConfigurationParameterizedTest {

    @ParameterizedTest
    @ValueSource(strings = { "Dilbert", "Wally" })
    void people(String name, @Autowired List<Person> people) {
        assertThat(people.stream()
          .map(Person::getName)
          .filter(name::equals)).hasSize(1);
    }
}
