package com.baeldung.beaninjectiontypes.config.filter;

import com.baeldung.beaninjectiontypes.demo.BasicEncoder;
import com.baeldung.beaninjectiontypes.demo.ConstructorEncoderService;
import com.baeldung.beaninjectiontypes.demo.Encoder;
import com.baeldung.beaninjectiontypes.demo.SetterEncoderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        value = {"com.baeldung.beaninjectiontypes.config.filter", "com.baeldung.beaninjectiontypes.demo"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        value = { SetterEncoderService.class, ConstructorEncoderService.class })
        })
public class FilteredDuplicateContextConfig {

    @Bean
    Encoder basicEncoder() {
        return new BasicEncoder();
    }

    @Bean
    Encoder duplicateEncoder() {
        return new BasicEncoder();
    }
}
