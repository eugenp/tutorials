package com.baeldung.typesofdispring;

import com.baeldung.typesofdispring.config.AppConfig;
import com.baeldung.typesofdispring.model.Booking;
import com.baeldung.typesofdispring.model.BookingWithAutowired;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertTrue;

public class DependencyInjectionTest {

    @Test
    public void whenConstructorInjection_thenDependencyValid() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Booking booking = context.getBean(Booking.class);

        assertTrue( "Constructor Injection", booking.getCustomer() != null );
        assertTrue( "Constructor Injection", booking.getProduct() != null );

        context.close();
    }

    @Test
    public void whenSetterInjection_thenDependencyValid() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Booking booking = context.getBean(Booking.class);

        assertTrue( "Setter Injection", booking.getInvoice() != null );

        context.close();
    }

    @Test
    public void givenAutowired_whenConstructorInjection_thenDependencyValid() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        BookingWithAutowired bookingWithAutowired = context.getBean(BookingWithAutowired.class);

        assertTrue( "Autowired Constructor Injection", bookingWithAutowired.getCustomer() != null );
        assertTrue( "Autowired Constructor Injection", bookingWithAutowired.getProduct() != null );

        context.close();
    }

    @Test
    public void givenAutowired_whenSetterInjection_thenDependencyValid() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        BookingWithAutowired bookingWithAutowired = context.getBean(BookingWithAutowired.class);

        assertTrue( "Autowired Setter Injection", bookingWithAutowired.getInvoice() != null );

        context.close();
    }
}
