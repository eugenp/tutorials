package com.baeldung.springbean.naming;

import com.baeldung.springbean.naming.component.Cat;
import com.baeldung.springbean.naming.component.Dog;
import com.baeldung.springbean.naming.service.PetShow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class SpringBeanNamingUnitTest {

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.scan("com.baeldung.springbean.naming");
        context.refresh();
    }

    // To name a bean spring gets the class name and converts the first letter to lowercase.
    // Default naming strategy of the spring bean which is using class level annotation

    @Test
    void givenLoggingServiceBeanIsCreated_whenThereIsNoValueProvided_thenBeanNameShouldBeDefaultName() {
        assertNotNull(context.getBean("loggingService"));
    }

    // In this case, to name a bean spring gets the class name and converts the first letter to lowercase.
    @Test
    void givenAuditServiceBeanIsCreatedWithMethodLevelAnnotation_whenThereIsNoValueProvided_thenBeanNameShouldBeTheNameOfMethod() {
        assertNotNull(context.getBean("audit"));
    }

    // To name a bean spring gets the class name and converts the first letter to lowercase.
    // Default naming strategy of the spring bean which is using class level annotation
    @Test
    void givenLoggingGatewayBeanIsCreatedWithFieldLevelAnnotation_whenThereIsNoValueProvided_thenBeanNameShouldBeDefaultName() {
        assertNotNull(context.getBean("loggingGateway"));
    }

    // spring will create the bean of type CustomComponent with the name "myBean".
    // As we're explicitly giving the name to the bean, spring will use this name to refer to it.
    @Test
    void givenCustomComponentBeanIsCreate_whenThereIsCustomNameGivenToBean_thenBeanShouldBeIdentifiedByThatName() {
        assertNotNull(context.getBean("myBean"));
    }

    @Test
    void givenCustomComponentBeanIsCreated_whenCustomNameIsGivenOnMethodLevelAnnotation_thenBeanShouldBeIdentifiedByThatName() {
        assertNotNull(context.getBean("beanComponent"));
        assertNotNull(context.getBean("configuration"));
        assertNotNull(context.getBean("qualifierComponent"));
    }

    @Test
    void givenMultipleImplementationsOfAnimal_whenFieldIsInjectedWithQualifiedName_thenTheSpecificBeanShouldGetInjected() {
        PetShow petShow = (PetShow) context.getBean("petShow");

        assertNotNull(context.getBean("cat"));
        assertNotNull(context.getBean("dog"));

        assertThat(petShow.getCat().getClass()).isEqualTo(Cat.class);
        assertThat(petShow.getDog().getClass()).isEqualTo(Dog.class);
    }
}
