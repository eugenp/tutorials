package com.baeldung.spring43.composedmapping;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.easymock.EasyMock.*;

@Configuration
@ComponentScan
@EnableWebMvc
public class ComposedMappingConfiguration {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public AppointmentService appointmentBook() {
        AppointmentService book = mock(AppointmentService.class);
        expect(book.getAppointmentsForToday()).andReturn(Collections.emptyMap());
        replay(book);
        return book;
    }

}
