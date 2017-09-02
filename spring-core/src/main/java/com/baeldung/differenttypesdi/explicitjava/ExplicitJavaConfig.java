package com.baeldung.differenttypesdi.explicitjava;

import com.baeldung.differenttypesdi.explicitjava.domain.ControlSignalReceptor;
import com.baeldung.differenttypesdi.explicitjava.domain.RemoteControl;
import com.baeldung.differenttypesdi.explicitjava.domain.Television;
import com.baeldung.differenttypesdi.shared.Control;
import com.baeldung.differenttypesdi.shared.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.baeldung.differenttypesdi.explicitjava"})
public class ExplicitJavaConfig {

    @Bean
    public Television television() {
        return new Television(processor(), controlSignalReceptor());
    }

    @Bean
    public Control remoteControl() {
        return new RemoteControl();
    }

    @Bean
    public ControlSignalReceptor controlSignalReceptor() {
        return new ControlSignalReceptor(processor());
    }

    @Bean
    public Processor processor() {
        return new Processor(2);
    }

}
