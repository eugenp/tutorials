package com.baeldung.beaninjectiontypes.config.manual;

import com.baeldung.beaninjectiontypes.demo.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        value = {"com.baeldung.beaninjectiontypes.config.manual", "com.baeldung.beaninjectiontypes.demo"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        value = { SetterEncoderService.class, ConstructorEncoderService.class })
        })
public class ManualContextConfig {

    @Bean
    Encoder basicEncoder() {
        return new BasicEncoder();
    }

    @Bean
    EncoderService constructorEncoderService(Encoder basicEncoder){
        return new ConstructorEncoderService(basicEncoder);
    }
}
