package com.baeldung.internalaop;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.Application;

@SpringBootTest(classes = Application.class)
class AddComponentIntegrationTest {

    @Resource
    private AddComponent addComponent;

    @Test
    void whenInternalCall_thenCacheNotHit() {
        addComponent.resetCache();

        addComponent.addOneAndDouble(0);

        assertThat(addComponent.getCounter()).isEqualTo(2);
    }

    @Test
    void whenExternalCall_thenCacheHit() {
        addComponent.resetCache();

        addComponent.addOne(0);
        addComponent.addOne(0);

        assertThat(addComponent.getCounter()).isEqualTo(1);
    }

}
