package com.baeldung.beaninjectiontypes.config.standard;

import com.baeldung.beaninjectiontypes.demo.BasicEncoder;
import com.baeldung.beaninjectiontypes.demo.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(value = {"com.baeldung.beaninjectiontypes.config.standard", "com.baeldung.beaninjectiontypes.demo"})
public class DefaultContextConfig {

    @Bean
    Encoder basicEncoder(){
        return new BasicEncoder();
    }
}
