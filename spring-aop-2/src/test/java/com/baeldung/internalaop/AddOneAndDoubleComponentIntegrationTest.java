package com.baeldung.internalaop;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.Application;

@SpringBootTest(classes = Application.class)
class AddOneAndDoubleComponentIntegrationTest {

    @Resource
    private AddOneAndDoubleComponent addOneAndDoubleComponent;

    @Resource
    private AddComponent addComponent;

    @Test
    void whenCallingFromExternalClass_thenAopProxyIsUsed() {
        addComponent.resetCache();

        addOneAndDoubleComponent.addOneAndDouble(0);

        assertThat(addComponent.getCounter()).isEqualTo(1);
    }
}
