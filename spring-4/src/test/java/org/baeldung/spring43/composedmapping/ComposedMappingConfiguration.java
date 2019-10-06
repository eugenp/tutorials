package org.baeldung.spring43.composedmapping;

import java.util.Collections;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.beans.factory.InjectionPoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;

import static org.easymock.EasyMock.replay;

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

    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint injectionPoint) {
        return LogManager.getLogger(injectionPoint.getField().getDeclaringClass());
    }

}
