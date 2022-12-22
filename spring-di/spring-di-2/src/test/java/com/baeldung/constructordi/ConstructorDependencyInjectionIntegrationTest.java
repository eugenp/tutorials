package com.baeldung.constructordi;

import com.baeldung.constructordi.domain.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class ConstructorDependencyInjectionIntegrationTest {

    @Test
    public void givenPrototypeInjection_WhenObjectFactory_ThenNewInstanceReturn() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Car firstContextCar = context.getBean(Car.class);

        ApplicationContext xmlContext = new ClassPathXmlApplicationContext("constructordi.xml");
        Car secondContextCar = xmlContext.getBean(Car.class);

        assertThat(firstContextCar).isNotSameAs(secondContextCar);
        assertThat(firstContextCar).hasToString("Engine: v8 5 Transmission: sliding");
        assertThat(secondContextCar).hasToString("Engine: v4 2 Transmission: sliding");
    }

}
