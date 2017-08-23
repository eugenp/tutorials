package com.baeldung.spring.bean.injection;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest @ImportResource(locations = "classpath:application-context.xml") @RunWith(SpringRunner.class) public abstract class AbstractApplicationXml {
}
