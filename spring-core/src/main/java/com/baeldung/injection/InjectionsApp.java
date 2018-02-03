package com.baeldung.injection;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration("InjectionsApp")
@ComponentScan({ "com.baeldung.injection" })
public class InjectionsApp {

    @Bean(name = "preferences", autowire = Autowire.BY_NAME)
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public UserPreferences getUserPreferences() {
        final UserPreferences preferences = new UserPreferences();
        return preferences;
    }

    @Bean(name = "constructorPreferences", autowire = Autowire.BY_NAME)
    public UserPreferences getUserConstructor() {
        final UserPreferences preferences = new UserPreferences("test");
        return preferences;
    }

}