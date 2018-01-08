package com.baeldung.typesofbeaninjection;

import com.baeldung.typesofbeaninjection.domain.Car;
import com.baeldung.typesofbeaninjection.domain.Engine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TypeOfBeanInjectionUnitTest {

    @Test
    public void whenConstructionInjectionInXML_thenCarHasEngine() {
        ApplicationContext applicationContext
          = new ClassPathXmlApplicationContext("/typesofbeaninjection-context.xml");
        Car car = applicationContext.getBean("alices-car", Car.class);

        checkEngine(car.getEngine());
    }

    @Test
    public void whenPropertyInjectionInXML_thenCarHasEngine() {
        ApplicationContext applicationContext
          = new ClassPathXmlApplicationContext("/typesofbeaninjection-context.xml");
        Car car = applicationContext.getBean("bobs-car", Car.class);

        checkEngine(car.getEngine());
    }

    @Test
    public void whenConstructionInjectionAnnotations_thenCarHasEngine() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        com.baeldung.typesofbeaninjection.domain.autowired.constructor.Car car
          = applicationContext.getBean(com.baeldung.typesofbeaninjection.domain.autowired.constructor.Car.class);

        checkEngine(car.getEngine());
    }

    @Test
    public void whenPropertyInjectionAnnotations_thenCarHasEngine() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        com.baeldung.typesofbeaninjection.domain.autowired.properties.Car car
           = applicationContext.getBean(com.baeldung.typesofbeaninjection.domain.autowired.properties.Car.class);

        checkEngine(car.getEngine());
    }

    private void checkEngine(Engine engine) {
        assertNotNull(engine);
        assertEquals("V8", engine.getType());
        assertEquals(5, engine.getVolume());
    }


}
