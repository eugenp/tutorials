package com.baeldung.di.setter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.di.setter.model.bean.Computer;
import com.baeldung.di.setter.model.bean.Memory;
import com.baeldung.di.setter.model.bean.Processor;

@Configuration
public class SpringBeanSetterContext {

    @Bean
    public Computer computer() {
        Computer computer = new Computer();
        computer.setProcessor(processor());
        computer.setMemory(memory());
        return computer;
    }

    @Bean
    public Processor processor() {
        return new Processor();
    }

    @Bean
    public Memory memory() {
        return new Memory();
    }

}
