package com.baeldung.typesofdispring;

import com.baeldung.typesofdispring.config.AppConfig;
import com.baeldung.typesofdispring.model.Booking;
import com.baeldung.typesofdispring.model.BookingWithAutowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Booking booking = context.getBean(Booking.class);
        BookingWithAutowired bookingAW = context.getBean(BookingWithAutowired.class);

        if (booking.getCustomer() != null) {
            System.out.println("Booking : Customer has injected through Constructor-based injection");
        }

        if (booking.getProduct() != null) {
            System.out.println("Booking : Product has injected through Constructor-based injection");
        }

        if (booking.getInvoice() != null) {
            System.out.println("Booking : Invoice has injected through Setter-based injection");
        }

        if (bookingAW.getCustomer() != null) {
            System.out.println("BookingWithAutowired : Customer has injected through @Autowired Constructor-based injection");
        }

        if (bookingAW.getProduct() != null) {
            System.out.println("BookingWithAutowired : Product has injected through @Autowired Constructor-based injection");
        }

        if (bookingAW.getInvoice() != null) {
            System.out.println("BookingWithAutowired : Invoice has injected through @Autowired Setter-based injection");
        }

        context.close();
    }
}
