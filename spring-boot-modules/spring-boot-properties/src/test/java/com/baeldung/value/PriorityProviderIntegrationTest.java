package com.baeldung.value;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PriorityProvider.class)
public class PriorityProviderIntegrationTest {

    @Autowired
    private PriorityProvider priorityProvider;

    @Test
    public void givenPropertyFileWhenConstructorInjectionUsedThenValueInjected() {
        assertThat(priorityProvider.getPriority()).isEqualTo("high");
    }
}