package com.baeldung.applicationstartuptracking;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
public class ApplicationTest {

    @Autowired
    private StartupTracker startupTracker;

    @Test
    void givenTheApplicationStarts_whenRetrieveRecordedEvents_ThenContainsCustumBeans() {

        startupTracker.recorded().stream().forEach(System.out::println);

        Assertions.assertThat(startupTracker.recorded()).contains(
            "spring.beans.instantiate  beanName specialService",
            "spring.beans.instantiate  beanName startupTracker"
        );
    }
}
