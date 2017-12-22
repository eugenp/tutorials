package com.baeldung.beaninjectiontypes;

import com.baeldung.beaninjectiontypes.constructor.Library;
import com.baeldung.beaninjectiontypes.setter.Hospital;
import com.baeldung.configuration.ApplicationContextTestBeanInjection;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.is;

public class BeanInjectionTypesTest {

    @Test
    public void whenInjectSetterBased_ThenValidBean() {
        ApplicationContext applicationContext =
          new AnnotationConfigApplicationContext(ApplicationContextTestBeanInjection.class);
        Hospital hospital = applicationContext.getBean(Hospital.class);
        String patientFirstName = hospital.getPatientName();
        Assert.assertThat("John", is(patientFirstName));
    }

    @Test
    public void whenInjectConstructorBased_ThenValidBean() {
        ApplicationContext applicationContext =
          new AnnotationConfigApplicationContext(ApplicationContextTestBeanInjection.class);
        Library library = applicationContext.getBean(Library.class);
        String managerName = library.getManagerName();
        Assert.assertThat("Stacy", is(managerName));
    }
}
