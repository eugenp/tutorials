package org.baeldung.spring43.composedmapping;

import java.util.Collections;

import org.easymock.EasyMock;
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
        AppointmentService book = EasyMock.mock(AppointmentService.class);
        EasyMock.expect(book.getAppointmentsForToday()).andReturn(Collections.emptyMap());
        replay(book);
        return book;
    }

}
