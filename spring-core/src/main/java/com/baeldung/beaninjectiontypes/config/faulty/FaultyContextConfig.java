package com.baeldung.beaninjectiontypes.config.faulty;

import com.baeldung.beaninjectiontypes.demo.BasicEncrypter;
import com.baeldung.beaninjectiontypes.demo.Encrypter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(value = {"com.baeldung.beaninjectiontypes.config.faulty", "com.baeldung.beaninjectiontypes.demo"})
public class FaultyContextConfig {

    @Bean
    Encrypter duplicateEncrypter(){
        return new BasicEncrypter();
    }

}
