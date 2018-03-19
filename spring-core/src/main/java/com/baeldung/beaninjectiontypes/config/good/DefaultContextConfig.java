package com.baeldung.beaninjectiontypes.config.good;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(value = {"com.baeldung.beaninjectiontypes.config.good", "com.baeldung.beaninjectiontypes.demo"})
public class DefaultContextConfig {


}
