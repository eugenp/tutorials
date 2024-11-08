package com.baeldung.userservice;

import com.baeldung.productservice.ProductApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserServiceApplication.class)
class SpringContextTest {
    
    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
