package com.baeldung.beaninjectiontypes;

import com.baeldung.beaninjectiontypes.domain.CPU;
import com.baeldung.beaninjectiontypes.domain.RAM;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes")
public class Config {

    @Bean
    public CPU Cpu() {
        return new CPU("cpu1", "Intel");
    }

    @Bean
    public RAM Ram() {
        return new RAM("ram1", "8GB");
    }
}
