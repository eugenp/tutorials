package com.baeldung.kafkasplitting;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SpringBootKafkaStreamsSplittingApp.class })
public class SpringBootKafkaStreamsSplittingContextIntegrationTest {

    @Test
    public void whenLoadApplication_thenSuccess() {

    }

}
