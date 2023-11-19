package com.baeldung.nullablebean;

import static org.junit.jupiter.api.Assertions.*;

import com.baeldung.nullablebean.nonrequired.NonRequiredConfiguration;
import com.baeldung.nullablebean.nonrequired.NonRequiredMainComponent;
import com.baeldung.nullablebean.nullablejava.NullableJavaConfiguration;
import com.baeldung.nullablebean.nullablejava.NullableMainComponent;
import com.baeldung.nullablebean.nullablespring.NullableConfiguration;
import com.baeldung.nullablebean.nullablespring.NullableSupplierConfiguration;
import com.baeldung.nullablebean.optionable.OptionableConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class NullableXMLComponentUnitTest {

    @Test
    void givenContextWhenCreatingNullableMainComponentThenSubComponentIsNull() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            NullableJavaConfiguration.class);
        final NullableMainComponent bean = context.getBean(NullableMainComponent.class);
        assertNull(bean.getSubComponent());
    }
    @Test
    void givenNonRequiredContextWhenCreatingMainComponentThenSubComponentIsNull() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            NonRequiredConfiguration.class);
        final NonRequiredMainComponent bean = context.getBean(NonRequiredMainComponent.class);
        assertNull(bean.getSubComponent());
    }

    @Test
    void givenOptionableContextWhenCreatingMainComponentThenSubComponentIsNull() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            OptionableConfiguration.class);
        final MainComponent bean = context.getBean(MainComponent.class);
        assertNull(bean.getSubComponent());
    }

    @Test
    void givenNullableSupplierContextWhenCreatingMainComponentThenSubComponentIsNull() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            NullableSupplierConfiguration.class);
        final MainComponent bean = context.getBean(MainComponent.class);
        assertNull(bean.getSubComponent());
    }

    @Test
    void givenNullableContextWhenCreatingMainComponentThenSubComponentIsNull() {
        assertThrows(UnsatisfiedDependencyException.class, () -> new AnnotationConfigApplicationContext(
            NullableConfiguration.class));
    }

    @Test
    void givenNullableXMLContextWhenCreatingMainComponentThenSubComponentIsNull() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "nullable-application-context.xml");
        final MainComponent bean = context.getBean(MainComponent.class);
        assertNull(bean.getSubComponent());
    }

    @Test
    void givenNullableSpELXMLContextWhenCreatingMainComponentThenSubComponentIsNull() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "nullable-spel-application-context.xml");
        final MainComponent bean = context.getBean(MainComponent.class);
        assertNull(bean.getSubComponent());
    }

    @Test
    void givenNullableSpELXMLContextWithNullablePropertiesWhenCreatingMainComponentThenSubComponentIsNull() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "nullable-configurable-spel-application-context.xml");
        final MainComponent bean = context.getBean(MainComponent.class);
        assertNull(bean.getSubComponent());
    }

    @Test
    void givenNullableSpELXMLContextWithNonNullablePropertiesWhenCreatingMainComponentThenSubComponentIsNull() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "non-nullable-configurable-spel-application-context.xml");
        final MainComponent bean = context.getBean(MainComponent.class);
        assertNotNull(bean.getSubComponent());
    }

    @Test
    void givenXMLContextWhenCreatingMainComponentThenSubComponentNotNull() {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "non-nullable-application-context.xml");
        final MainComponent bean = context.getBean(MainComponent.class);
        assertNotNull(bean.getSubComponent());
    }
}