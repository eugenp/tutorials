package com.baeldung.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.baeldung.value.SomeBean;

@Configuration
@ComponentScan({ "com.baeldung.injection" })
public class App {
    @Autowired
    private SomeBean someBean;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public UserPreferences getUserPreferences() {
        final UserPreferences preferences = new UserPreferences();
        return preferences;
    }
}
