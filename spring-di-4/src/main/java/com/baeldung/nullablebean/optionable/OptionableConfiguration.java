package com.baeldung.nullablebean.optionable;

import com.baeldung.nullablebean.MainComponent;
import com.baeldung.nullablebean.SubComponent;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OptionableConfiguration {

    @Bean
    public MainComponent mainComponent(Optional<SubComponent> optionalSubComponent) {
        return new MainComponent(optionalSubComponent.orElse(null));
    }

}
