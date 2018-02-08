package com.baeldung.beansinjectiontypes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.baeldung.beansinjectiontypes.constructor.Config;
import com.baeldung.beansinjectiontypes.constructor.Dealership;

public class BeansInjectionTypesIntegrationTest {

    @Test
    public void configBean_WhenSetOnConstructor_ThenDependencyValid() {
        try (AnnotationConfigApplicationContext context = 
          new AnnotationConfigApplicationContext(Config.class)) {
            Dealership dealership = (Dealership) context.getBean("dealership");

            assertTrue(dealership.getCar() instanceof Sedan);
        }
    }

    @Test
    public void configBean_WhenSetOnSetter_ThenDependencyValid() {
        try (AnnotationConfigApplicationContext context = 
          new AnnotationConfigApplicationContext(com.baeldung.beansinjectiontypes.setter.Config.class)) {
            com.baeldung.beansinjectiontypes.setter.Dealership dealership = 
              (com.baeldung.beansinjectiontypes.setter.Dealership) context.getBean("dealership");

            assertTrue(dealership.getCar() instanceof Sedan);
        }        
    }

    @Test
    public void annotationAndXML_WhenSetOnSetter_ThenDependencyValid() {
        try (FileSystemXmlApplicationContext context = 
          new FileSystemXmlApplicationContext("src/main/resources/beansinjectiontypes-ctx.xml")) {
            Dealership dealership = (Dealership) context.getBean("constructorDealership");

            assertTrue(dealership.getCar() instanceof Sedan);
        }        
    }

    @Test
    public void annotationAndXML_WhenSetOnConstructor_ThenDependencyValid() {
        try (FileSystemXmlApplicationContext context = 
          new FileSystemXmlApplicationContext("src/main/resources/beansinjectiontypes-ctx.xml")) {
            com.baeldung.beansinjectiontypes.setter.Dealership dealership = 
                (com.baeldung.beansinjectiontypes.setter.Dealership) context.getBean("setterDealership");

            assertTrue(dealership.getCar() instanceof Sedan);
        }        
    }

}
