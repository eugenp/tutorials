package com.baeldung.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CollectionProvider.class)
public class CollectionProviderIntegrationTest {

    @Autowired
    private CollectionProvider collectionProvider;

    @Test
    public void givenPropertyFileWhenSetterInjectionUsedThenValueInjected() {
        assertThat(collectionProvider.getValues()).contains("A", "B", "C");
    }
}