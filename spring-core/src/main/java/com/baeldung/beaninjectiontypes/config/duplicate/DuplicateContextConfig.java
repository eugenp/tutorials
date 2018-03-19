package com.baeldung.beaninjectiontypes.config.duplicate;

import com.baeldung.beaninjectiontypes.demo.BasicEncoder;
import com.baeldung.beaninjectiontypes.demo.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(value = {"com.baeldung.beaninjectiontypes.config.duplicate", "com.baeldung.beaninjectiontypes.demo"})
public class DuplicateContextConfig {

    @Bean
    Encoder basicEncoder(){
        return new BasicEncoder();
    }

    @Bean
    Encoder duplicateEncoder(){
        return new BasicEncoder();
    }

}
